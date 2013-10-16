package hackathon;

import hackathon.roomavailability.Availability;
import hackathon.roomavailability.RoomAvailabilityService;
import hackathon.roomavailability.Slot;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;

/**
 * try this out!!
 */
public class App {
  public static void main(String[] args) throws Exception {
    Calendar c = new GregorianCalendar();
    c.set(Calendar.HOUR_OF_DAY, 0); // anything 0 - 23
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    Date startDate = c.getTime();
    c.add(Calendar.DATE, 1);
    Date endDate = c.getTime();

    if (args.length != 2) {
      throw new IllegalStateException("Usage: program userEmail password");
    }
    final RoomAvailabilityService service = new RoomAvailabilityService(args[0], args[1]);

    for (String room : Arrays.asList("sapphire", "ocean", "rainbow", "lake", "desert", "hub")) {
      System.out.println("******"+room);
      System.out.println(service.getRoomInfo(room));

      Availability slotList = service.getAvailablity(room, new DateTime(startDate), new DateTime(endDate));
      for (Slot slot : slotList.getSlots()) {
        System.out.println(slot);
      }
    }

    // Book a room. Have to delete manually.
 /*   DateTime start = DateTime.now().plusHours(1).withMinuteOfHour(0).withMillis(0);
    service.bookRoom("violet", start, start.plusMinutes(30), "TEST MEETING");
 */ }
}
