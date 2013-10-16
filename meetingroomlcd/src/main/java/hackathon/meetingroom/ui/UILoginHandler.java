package hackathon.meetingroom.ui;

import com.fasterxml.jackson.dataformat.yaml.snakeyaml.util.UriEncoder;
import hackathon.meetingroom.auth.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/login")
@Produces(MediaType.TEXT_HTML)
public class UILoginHandler {

  @GET
  public LoginView login(@QueryParam("redirectTo") String redirectTo) {
    return new LoginView(redirectTo);
  }

  @POST
  @Path("auth")
  public Response auth(@Context HttpServletRequest request, @FormParam("redirectTo") String redirectTo, @FormParam("user") String user, @FormParam("password") String password) {
    request.getSession().setAttribute("user", new User(user, password));
    return Response
            .temporaryRedirect(URI.create(redirectTo))
            .status(302)
            .build();
  }

  public static Response authResponse(String redirectTo) {
    return Response
            .temporaryRedirect(URI.create("/login?redirectTo=" + UriEncoder.encode(redirectTo)))
            .build();
  }
}
