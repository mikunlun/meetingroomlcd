package hackathon.roomavailability;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: mstein
 */
public class RoomInfo {
  private static Pattern pattern = Pattern.compile("Conf ([A-Z][A-Z])\\-US ([EW][0-9])\\-(\\d\\d) (\\w*)\\s*(\\(no AV\\))*");
  private final String _emailAddress;
  private final String _name;
  private final boolean _hasAV;
  private final int _capacity;
  private final String _description;
  private final String _floor;

  public RoomInfo(String description, String address) {
    _description = description;
    _emailAddress = address;
    // Description looks like this: Conf FC-US E8-04 Sapphire (no AV), or: Conf FC-US E8-12 Ocean
    Matcher matcher = pattern.matcher(description);
    if (!matcher.find()) {
      _capacity = 0;
      _hasAV = false;

      _name = _description;
      _floor = "unknown";
      return;
    }
    _capacity = Integer.parseInt(matcher.group(3));
    _hasAV = matcher.group(5) != null;
    _name = matcher.group(4);
    _floor = matcher.group(2);
  }

  public static Pattern getPattern() {
    return pattern;
  }

  public String getEmailAddress() {
    return _emailAddress;
  }

  public boolean isHasAV() {
    return _hasAV;
  }

  public int getCapacity() {
    return _capacity;
  }

  public String getDescription() {
    return _description;
  }

  public String getFloor() {
    return _floor;
  }

  public String getName() {
    return _name;
  }

  @Override
  public String toString() {
    return "RoomInfo{" +
            "_emailAddress='" + _emailAddress + '\'' +
            ", _name='" + _name + '\'' +
            ", _hasAV=" + _hasAV +
            ", _capacity=" + _capacity +
            ", _description='" + _description + '\'' +
            ", _floor='" + _floor + '\'' +
            '}';
  }
}
