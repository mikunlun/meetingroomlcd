package hackathon.meetingroom.webclient.api;

import hackathon.roomavailability.Availability;
import hackathon.roomavailability.BookedSlot;
import hackathon.roomavailability.FreeSlot;
import hackathon.roomavailability.Slot;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Period;

import java.util.Collection;

public class BookingResponse {

    public static AvailabilityResponse buildFrom(Availability avail) {

        Slot firstBookSlot = null;
        Slot firstFreeSlot = null;
        boolean currentFree = false;
        Collection<Slot> slots = avail.getSlots();
        int iSlot = 0;
        for (Slot slot : slots) {
            if (slot instanceof FreeSlot) {
                if (firstFreeSlot == null) {
                    firstFreeSlot = slot;
                }
                if (iSlot == 0) {
                    currentFree = true;
                }
            } else {
                if (firstBookSlot == null) {
                    firstBookSlot = slot;
                }
            }
            iSlot++;
        }

        AbstractAvailabilityResponse response;

        DateTime now = DateTime.now();
        int start;
        int lengthFree;

        if (currentFree) {
          AvailabilityFreeResponse resp = new AvailabilityFreeResponse();
          start = 0;
          lengthFree = firstFreeSlot.getDurationInMinutes();
          Period period = new Period(now, firstFreeSlot.getEndDateTime());
          int freeFor = Minutes.standardMinutesIn(period).getMinutes();

          resp.withFreeFor(Integer.toString(freeFor));
          if (firstBookSlot != null) {
            BookedSlot booked = ((BookedSlot) firstBookSlot);
            resp.withOwner(booked.getOwner()).withTitle(booked.getTitle()).withDuration(booked.getDurationInMinutes());
          } else {
            resp.withNoMeeting();
          }
          response = resp;
        } else {
          AvailabilityBookResponse resp = new AvailabilityBookResponse();

          if (firstBookSlot != null) {
            BookedSlot booked = (BookedSlot) firstBookSlot;
            resp.withOwner(booked.getOwner()).withTitle(booked.getTitle()).withDuration(booked.getDurationInMinutes());
          } else {
            resp.withNoMeeting();
          }

          int untilNextFree;
          if (firstFreeSlot != null) {
            Period period = new Period(now, firstFreeSlot.getStartDateTime());
            untilNextFree = Minutes.standardMinutesIn(period).getMinutes();
            lengthFree = firstFreeSlot.getDurationInMinutes();
          } else {
            untilNextFree = 24 * 60;
            lengthFree = 0;
          }
          start = untilNextFree;
          resp.withUntilNextFree(Integer.toString(untilNextFree));
          response = resp;
        }

        int duration = 30;
        while (true) {
          boolean last = duration >= 2 * 60 || duration >= lengthFree;
          duration = Math.min(duration, lengthFree);
          if (duration > 0) {
            Booking partial = new Booking();
            partial.withDuration(duration);
            partial.withStartTime(now.plusMinutes(start).toString("HH:mm"));
            partial.withEndTime(now.plusMinutes(start + duration).toString("HH:mm"));
            response.getBookFor().add(partial);
          }
          if (last) {
            break;
          }
          duration += 30;
        }
        return response;
    }

}
