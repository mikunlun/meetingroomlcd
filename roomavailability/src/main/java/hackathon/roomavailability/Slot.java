package hackathon.roomavailability;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Period;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Slot {

    DateTime start;
    DateTime end;
    Period   _period;

    public Slot(DateTime startTime, DateTime endTime) {
        start = startTime;
        end = endTime;
        // For booked slots, this is only the preliminary period! We add the
        // subsequent booked slot periods in a later step.
        _period = new Period(startTime, endTime);
    }

    @JsonProperty("type")
    public String type() {
        return getClass().getSimpleName();
    }

    @JsonProperty("startTime")
    public String getStartTime() {
        return start.toString("HH:mm");
    }

    @JsonProperty("endTime")
    public String getEndTime() {
        return end.toString("HH:mm");
    }

    @JsonProperty("duration")
    public int getDurationInMinutes() {
        return Minutes.standardMinutesIn(_period).getMinutes();
    }

    @JsonIgnore
    public DateTime getStartDateTime() {
        return start;
    }

    @JsonIgnore
    public DateTime getEndDateTime() {
        return end;
    }

    @JsonIgnore
    Period getPeriod() {
        return _period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Slot)) {
            return false;
        }

        Slot slot = (Slot) o;

        if (!end.equals(slot.end)) {
            return false;
        }
        if (!start.equals(slot.start)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = start.hashCode();
        result = 31 * result + end.hashCode();
        return result;
    }
}
