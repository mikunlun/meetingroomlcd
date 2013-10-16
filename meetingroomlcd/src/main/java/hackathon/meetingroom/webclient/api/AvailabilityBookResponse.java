package hackathon.meetingroom.webclient.api;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AvailabilityBookResponse extends AbstractAvailabilityResponse {
    String             _untilNextFree;

    public AvailabilityBookResponse() {
    }

    @JsonGetter("untilNextFree")
    public String getUntilNextFree() {
        return _untilNextFree;
    }

    @JsonIgnore
    public AvailabilityBookResponse withUntilNextFree(String untilNextFree) {
        _untilNextFree = untilNextFree;
        return this;
    }

}
