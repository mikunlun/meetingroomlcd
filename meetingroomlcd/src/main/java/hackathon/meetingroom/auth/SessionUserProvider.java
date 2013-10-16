package hackathon.meetingroom.auth;

import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;
import hackathon.meetingroom.ui.UILoginHandler;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Type;

@Provider
public class SessionUserProvider implements Injectable<User>, InjectableProvider<SessionUser, Type> {

  private final HttpServletRequest request;

  public SessionUserProvider(@Context HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public Injectable<User> getInjectable(ComponentContext cc, SessionUser a, Type c) {
    if (c.equals(User.class)) {
      return this;
    }
    return null;
  }

  @Override
  public ComponentScope getScope() {
    return ComponentScope.PerRequest;
  }

  @Override
  public User getValue() {
    final User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
      throw new WebApplicationException(UILoginHandler.authResponse(request.getRequestURI()));
    }
    return user;
  }

}