package hackathon.roomavailability;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Period;

import com.fasterxml.jackson.annotation.JsonGetter;

public class FreeSlot extends Slot {

    public FreeSlot(DateTime startTime, DateTime endTime) {
        super(startTime, endTime);
    }

    @JsonGetter("freeFor")
    public int getFreeForInMinutes() {
        Period free = new Period(DateTime.now(), end);
        return Minutes.standardMinutesIn(free).getMinutes();
    }

    @Override
    public String toString() {
        return "FreeSlot{" + "freeFor=" + _period + '}';
    }
}
