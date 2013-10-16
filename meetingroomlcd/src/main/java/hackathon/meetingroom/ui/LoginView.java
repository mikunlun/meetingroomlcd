package hackathon.meetingroom.ui;

public class LoginView extends BaseView {

  private final String redirectTo;

  public LoginView(String redirectTo) {
    super("LoginView.ftl");
    this.redirectTo = redirectTo;
  }

  public String getRedirectTo() {
    return redirectTo;
  }
}