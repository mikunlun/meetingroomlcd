package hackathon.meetingroom.webclient.api;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstractAvailabilityResponse implements AvailabilityResponse {
    Meeting       _meeting = new Meeting();
    List<Booking> _bookFor = new ArrayList<Booking>();

    public AbstractAvailabilityResponse() {
    }

    @JsonGetter("meeting")
    public Meeting getMeeting() {
        return _meeting;
    }

    @JsonGetter("bookFor")
    public List<Booking> getBookFor() {
        return _bookFor;
    }
    
    @JsonIgnore
    public AbstractAvailabilityResponse withNoMeeting() {
        _meeting = null;
        return this;
    }
    

    @JsonIgnore
    public AbstractAvailabilityResponse withOwner(String owner) {
        _meeting.withOwner(owner);
        return this;
    }

    @JsonIgnore
    public AbstractAvailabilityResponse withTitle(String title) {
        _meeting.withTitle(title);
        return this;
    }

    @JsonIgnore
    public AbstractAvailabilityResponse withDuration(Integer duration) {
        _meeting.withDuration(duration);
        return this;
    }

}
