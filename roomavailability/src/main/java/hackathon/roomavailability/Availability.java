package hackathon.roomavailability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Availability {

    public enum CurrentStatus {
        Booked, Free
    };

    private List<Slot> _slots = new ArrayList<Slot>();

    public Collection<Slot> getSlots() {
        return _slots;
    }

    public void add(Slot slot) {
        _slots.add(slot);
    }

    @JsonProperty("currentstatus") 
    public CurrentStatus getStatus() {
        if (!_slots.isEmpty()) {
            Slot first = _slots.iterator().next();
            if (first instanceof BookedSlot) {
                return CurrentStatus.Booked;
            } else if (first instanceof FreeSlot) {
                return CurrentStatus.Free;
            }
        }
        return CurrentStatus.Free;// assume free
    }

}
