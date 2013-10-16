package hackathon.meetingroom.ui;

public class RoomView extends BaseView {
  private final String name;

  public RoomView(String name) {
    super("RoomView.ftl");
    this.name = name;
  }

  public String getName() {
    return name;
  }
}