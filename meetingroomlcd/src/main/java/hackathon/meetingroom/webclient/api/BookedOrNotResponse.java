package hackathon.meetingroom.webclient.api;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookedOrNotResponse {

    private String _msg;

    public BookedOrNotResponse(@JsonProperty("message") String msg) {
        _msg = msg;
    }

    @JsonGetter("message")
    public String getMessage() {
        return _msg;
    }

}
