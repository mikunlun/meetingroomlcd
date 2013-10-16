package hackathon.meetingroom.location;

public class MeetingRoomLocation {

    String _name;
    int    _x;
    int    _y;

    public MeetingRoomLocation(String name, String x, String y) {
        _name = name;
        _x = Integer.parseInt(x);
        _y = Integer.parseInt(y);
    }

    public String get_name() {
        return _name;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MeetingRoomLocation [_name=").append(_name).append(", _x=").append(_x).append(", _y=").append(_y).append("]");
        return builder.toString();
    }

}
