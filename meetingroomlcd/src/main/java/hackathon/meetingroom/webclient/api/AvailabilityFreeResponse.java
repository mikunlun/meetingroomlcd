package hackathon.meetingroom.webclient.api;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AvailabilityFreeResponse extends AbstractAvailabilityResponse{
    String             _freeFor;

    public AvailabilityFreeResponse() {
    }

 
    @JsonGetter("freeFor")
    public String getFreeFor() {
        return _freeFor;
    }

    @JsonIgnore
    public AvailabilityFreeResponse withFreeFor(String freeFor) {
        _freeFor = freeFor;
        return this;
    }


}
