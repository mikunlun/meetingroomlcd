package hackathon.roomavailability;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Period;

import com.fasterxml.jackson.annotation.JsonGetter;

public class BookedSlot extends Slot {
    String _owner;
    String _title;

    public BookedSlot(String smtpAddress, DateTime startTime, DateTime endTime, String title) {
        super(startTime, endTime);
        _owner = smtpAddress;
        _title = title;
    }

    /**
     * 
     * @return the time until the room is free
     */
    @JsonGetter("untilNextFree")
    public int getUntilNextFreeInMinutes() {
        Period free = new Period(DateTime.now(), start);
        return Minutes.standardMinutesIn(free).getMinutes();
    }

    @JsonGetter("owner")
    public String getOwner() {
        return _owner;
    }

    @JsonGetter("title")
    public String getTitle() {
        return _title;
    }

    @Override
    public String toString() {
        return "BookedSlot{" + "owner='" + _owner + '\'' + ", title='" + _title + '\'' + ", _period=" + _period + '}';
    }
}
