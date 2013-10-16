package hackathon.meetingroom.webclient.api;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Meeting {
    String _owner;
    String _title;
    Integer _duration;

    public Meeting() {
    }

    public Meeting(@JsonProperty("owner") String owner, @JsonProperty("title") String title, @JsonProperty("duration") Integer duration) {
        _owner = owner;
        _title = title;
        _duration = duration;
    }

    @JsonGetter("owner")
    public String getOwner() {
        return _owner;
    }

    @JsonGetter("title")
    public String getTitle() {
        return _title;
    }

    @JsonGetter("duration")
    public Integer getDuration() {
        return _duration;
    }

    @JsonIgnore
    public Meeting withOwner(String owner) {
        _owner = owner;
        return this;
    }

    @JsonIgnore
    public Meeting withTitle(String title) {
        _title = title;
        return this;
    }

    @JsonIgnore
    public Meeting withDuration(Integer duration) {
        _duration = duration;
        return this;
    }
}
