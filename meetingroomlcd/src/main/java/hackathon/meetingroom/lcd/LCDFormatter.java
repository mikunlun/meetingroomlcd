package hackathon.meetingroom.lcd;

import hackathon.roomavailability.Availability;
import hackathon.roomavailability.BookedSlot;
import hackathon.roomavailability.Slot;
import hackathon.display.lcd.rest.api.Display;
import hackathon.meetingroom.webclient.api.AvailabilityBookResponse;
import hackathon.meetingroom.webclient.api.AvailabilityFreeResponse;
import hackathon.meetingroom.webclient.api.AvailabilityResponse;
import hackathon.meetingroom.webclient.api.BookingResponse;
import hackathon.meetingroom.webclient.api.Meeting;

import java.util.Iterator;

import org.joda.time.Minutes;
import org.joda.time.Period;

public class LCDFormatter {

    public static Display fromAvailability(Availability avail) {
        AvailabilityResponse resp = BookingResponse.buildFrom(avail);
        Display disp = new Display().withValidationOff();

        if (resp instanceof AvailabilityBookResponse) {
            AvailabilityBookResponse book = (AvailabilityBookResponse) resp;

            String untilFree = book.getUntilNextFree();
            String untilFreeStr = formatMinutes(untilFree);
            Meeting mtg = book.getMeeting();
            String owner = mtg.getOwner();
            String title = stripName(owner, mtg.getTitle());

            disp.withLine1("Free in:");
            disp.withLine2(untilFreeStr);
            disp.withLine3(owner);
            disp.withLine4(title);

        } else if (resp instanceof AvailabilityFreeResponse) {

            AvailabilityFreeResponse free = (AvailabilityFreeResponse) resp;
            String freeFor = free.getFreeFor();
            String freeForStr = formatMinutes(freeFor);

            disp.withLine1("Free for:");
            disp.withLine2(freeForStr);

            Iterator<Slot> is = avail.getSlots().iterator();
            while (is.hasNext()) {
                Slot s = is.next();
                if (s instanceof BookedSlot) {
                    BookedSlot b = (BookedSlot) s;
                    String start = b.getStartTime();
                    String owner = b.getOwner();
                    disp.withLine3("Next: " + start);
                    disp.withLine4(owner);
                    break;
                }
            }
        }

        return disp;
    }

    private static String stripName(String owner, String title) {
        int idx = findNameIdx(title);

        if (idx > 0 && title.contains(owner)) {
            return title.substring(idx + 1);
        }

        return title;
    }

    private static int findNameIdx(String title1) {
        int s = 0;
        for (int i = 0; i < title1.length(); i++) {
            if (title1.charAt(i) == ' ') {
                s += 1;
                if (s == 2) {
                    return i;
                }
            }
        }
        return 0;
    }

    private static String formatMinutes(String minutes) {
        if (minutes == null || "".equals(minutes)) {
            return "";
        }
        int mins = Integer.parseInt(minutes);
        if (mins < 60) {
            return Integer.valueOf(mins) + " minutes";
        } else {
            Minutes m = Minutes.minutes(mins);
            Period p = m.toPeriod();
            int hh = p.toStandardHours().getHours();
            int mm = p.minusHours(hh).toStandardMinutes().getMinutes();
            return hh + " hours, " + mm + " minutes";
        }
    }
}
