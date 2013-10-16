package hackathon.roomavailability;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import microsoft.exchange.webservices.data.Appointment;
import microsoft.exchange.webservices.data.Attendee;
import microsoft.exchange.webservices.data.AttendeeAvailability;
import microsoft.exchange.webservices.data.AttendeeInfo;
import microsoft.exchange.webservices.data.AvailabilityData;
import microsoft.exchange.webservices.data.BodyType;
import microsoft.exchange.webservices.data.CalendarEvent;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;
import microsoft.exchange.webservices.data.GetUserAvailabilityResults;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.NameResolution;
import microsoft.exchange.webservices.data.NameResolutionCollection;
import microsoft.exchange.webservices.data.SendInvitationsMode;
import microsoft.exchange.webservices.data.ServiceError;
import microsoft.exchange.webservices.data.TimeWindow;
import microsoft.exchange.webservices.data.WebCredentials;
import microsoft.exchange.webservices.data.WellKnownFolderName;

import org.joda.time.DateTime;

/**
 * User: mstein
 */
public class RoomAvailabilityService {

    private static final String   EXCHANGEURL = System.getProperty("ews.exchangeurl");
    private static final String   DOMAIN      = System.getProperty("ews.domain");
    final private ExchangeService _ews;
    final private String          _userEmail;

    public RoomAvailabilityService(String user, String password) {
        _ews = connectExchange(user, password);
        _userEmail = makeEmailAddress(user);
    }

    private ExchangeService connectExchange(String user, String password) {
        ExchangeCredentials credentials = new WebCredentials(user, password);
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        service.setCredentials(credentials);
        try {
            service.setUrl(new URI(EXCHANGEURL));
            // Test if this works.
            service.getInboxRules();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Could set Exchange URL!", e);
        } catch (Exception e) {
            throw new IllegalStateException("Could not connect to Exchange at " + EXCHANGEURL + " with user " + user + " error: " + e.getMessage(), e);
        }
        return service;
    }

    /**
     * This gets the availability records for a room. It should be easy to use
     * for an application that wants to show what the current and future status
     * of a room is.
     * 
     * @param roomName
     *            the name of the room. It will be expanded with
     *            croom-X@guidewire.com to check with Exchange.
     * @param start
     *            start time when to check for availability
     * @param end
     *            end time
     * @throws Exception
     */
    public Availability getAvailablity(String roomName, DateTime start, DateTime end) throws Exception {
        List<AttendeeInfo> attendees = new ArrayList<AttendeeInfo>();
        attendees.add(new AttendeeInfo(makeRoomAddress(roomName)));
        // Could add more attendees here...

        // Call the Exchange availability service.
        GetUserAvailabilityResults availability = _ews.getUserAvailability(attendees, new TimeWindow(start.toDate(), end.toDate()), AvailabilityData.FreeBusyAndSuggestions);

        Availability availabilitySlotList = new Availability();
        if (availability.getAttendeesAvailability().getCount() != 1) {
            throw new IllegalStateException("Attendee count not equal to one!");
        }
        AttendeeAvailability attendeeAvailability = availability.getAttendeesAvailability().iterator().next();
        // This loop should contain only one element, the room we're looking at.
        if (attendeeAvailability.getErrorCode() != ServiceError.NoError) {
            throw new IllegalStateException("service error: " + attendeeAvailability.getErrorMessage());
        }
        fillSlots(end, availabilitySlotList, attendeeAvailability, attendees.get(0));
        calculateBookedSlotTimeUntilFree(availabilitySlotList);
        return availabilitySlotList;
    }

    // Find each booked slot and set the period (until free) to the sum of the
    // subsequent booked slots.
    private void calculateBookedSlotTimeUntilFree(Availability availabilitySlotList) {
        // Go through all the booked slots and set the time until free.
        for (Slot slot : availabilitySlotList.getSlots()) {
            if (slot instanceof BookedSlot) {
                boolean beforeOuterSlot = true;
                for (Slot innerSlot : availabilitySlotList.getSlots()) {
                    // Go over the slots after the booked slot and add up
                    // periods of subsequent booked slots.
                    if (beforeOuterSlot && innerSlot.equals(slot)) {
                        beforeOuterSlot = false;
                        continue;
                    }
                    if (!beforeOuterSlot) {
                        // Add up periods of booked slots for subsequent booked
                        // slots.
                        if (innerSlot instanceof FreeSlot) {
                            break;
                        }
                        slot._period = slot._period.plus(innerSlot.getPeriod());
                    }
                }
            }
        }
    }

    // private Collection<TimeZoneDefinition> getTimezones() {
    // Collection<TimeZoneDefinition> timezones = _ews.getServerTimeZones();
    // return timezones;
    // }

    private String makeRoomAddress(String roomName) {
        return "croom-" + roomName.toLowerCase() + DOMAIN;
    }

    private String makeEmailAddress(String user) {
        return user + DOMAIN;
    }

    private void fillSlots(DateTime end, Availability availabilitySlotList, AttendeeAvailability attendeeAvailability, AttendeeInfo attendeeInfo) {
        // The last event start time, so we can properly create free/busy slots.
        DateTime lastEventEnd = DateTime.now();
        for (CalendarEvent calendarEvent : attendeeAvailability.getCalendarEvents()) {
            DateTime eventStart = new DateTime(calendarEvent.getStartTime());
            DateTime eventEnd = new DateTime(calendarEvent.getEndTime());
            if (eventStart.isBeforeNow() && eventEnd.isBeforeNow()) {
                continue;
            }
            if (eventStart.isAfter(lastEventEnd.plusMinutes(5))) {
                // If the event is more than 5 minutes in the future we insert a
                // free slot.
                availabilitySlotList.add(new FreeSlot(lastEventEnd, new DateTime(calendarEvent.getStartTime())));
            }

            // Find owner
            // String calendarId = calendarEvent.getDetails().getStoreId();
            try {
                String organizer;
                // // This doesn;t seem to work.
                // AlternateId alternateId = (AlternateId) _ews.convertId(new
                // AlternateId(IdFormat.HexEntryId, calendarId,
                // attendeeInfo.getSmtpAddress()), IdFormat.EwsId);
                // Appointment appointment = Appointment.bind(_ews, new
                // ItemId(alternateId.getUniqueId()));
                // organizer = appointment.getOrganizer().getName();

                organizer = calendarEvent.getDetails().getSubject();
                String[] organizerParts = organizer.split(" ");
                organizer = organizerParts[0] + " " + organizerParts[1]; // attendeeInfo.getSmtpAddress();
                                                                         // //
                                                                         // ugly!!!
                // Add a busy slot for this event.
                availabilitySlotList.add(new BookedSlot(organizer, new DateTime(calendarEvent.getStartTime()), new DateTime(calendarEvent.getEndTime()), calendarEvent.getDetails()
                        .getSubject()));
                lastEventEnd = new DateTime(calendarEvent.getEndTime());
            } catch (Exception e) {
                throw new IllegalStateException("couldnt get calendar event", e);
            }
        }
        // If there is time after the last event end, add a free slot.
        if (lastEventEnd.isBefore(end)) {
            availabilitySlotList.add(new FreeSlot(lastEventEnd, end));
        }
    }

    /**
     * Book a room under the logged in user.
     * 
     * @param roomName
     *            room name (lake, ocean, ...)
     * @param start
     *            time
     * @param end
     *            time
     * @param title
     *            subject line of meeting
     */
    public void bookRoom(String roomName, DateTime start, DateTime end, String title) {
        try {
            Appointment appointment = new Appointment(_ews);
            appointment.setStart(start.toDate());
            appointment.setEnd(end.toDate());
            appointment.setSubject(title);
            MessageBody body = new MessageBody();
            body.setText("Booked by hackathon on: " + DateTime.now());
            body.setBodyType(BodyType.Text);
            appointment.setBody(body);

            appointment.getRequiredAttendees().add(roomName, _userEmail);
            appointment.getRequiredAttendees().add(new Attendee(roomName, makeRoomAddress(roomName)));

            // appointment.setStartTimeZone(getTimezones().);
            appointment.setLocation(roomName);

            appointment.save(WellKnownFolderName.Calendar, SendInvitationsMode.SendOnlyToAll);

        } catch (Exception e) {
            throw new IllegalStateException("Could not make new Appointment " + e.getMessage(), e);
        }
    }

    /**
     * Conference room names look like this: Conf FC-US E8-04 Sapphire (no AV)
     * or: Conf FC-US E8-12 Ocean
     * 
     * @param roomName
     *            name of room (lake, ocean, ...)
     * @return
     */
    public RoomInfo getRoomInfo(String roomName) {
        NameResolutionCollection nameResolutions = null;
        try {
            nameResolutions = _ews.resolveName(makeRoomAddress(roomName));
        } catch (Exception e) {
            throw new IllegalStateException("Could not resolve room " + roomName, e);
        }

        if (nameResolutions.getCount() == 0) {
            return null;
        }
        if (nameResolutions.getCount() > 1) {
            throw new IllegalStateException("Found more than one room matching " + roomName);
        }
        for (NameResolution nameResolution : nameResolutions) {
            return new RoomInfo(nameResolution.getMailbox().getName(), nameResolution.getMailbox().getAddress());
        }
        return null;
    }

}
