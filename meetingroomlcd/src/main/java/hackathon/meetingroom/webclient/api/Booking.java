package hackathon.meetingroom.webclient.api;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Booking {
    Integer _duration;
    String  _startTime;
    String  _endTime;

    public Booking() {

    }

    public Booking(@JsonProperty("duration") Integer duration, @JsonProperty("startTime") String startTime, @JsonProperty("endTime") String endTime) {
        _duration = duration;
        _startTime = startTime;
        _endTime = endTime;
    }

    @JsonGetter("duration")
    public Integer getDuration() {
        return _duration;
    }

    @JsonGetter("startTime")
    public String getStartTime() {
        return _startTime;
    }

    @JsonGetter("endTime")
    public String getEndTime() {
        return _endTime;
    }

    @JsonIgnore
    public Booking withDuration(Integer duration) {
        _duration = duration;
        return this;
    }

    @JsonIgnore
    public Booking withStartTime(String startTime) {
        _startTime = startTime;
        return this;
    }

    @JsonIgnore
    public Booking withEndTime(String endTime) {
        _endTime = endTime;
        return this;
    }

}
