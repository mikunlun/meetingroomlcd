package hackathon.meetingroom.ui;

import com.yammer.dropwizard.views.View;

import java.util.Random;

public abstract class BaseView extends View{

  public BaseView(String templateName) {
    super(templateName);
  }

  public String getAssetToken() {
    return "?" + Math.abs(new Random().nextInt());
  }
}
