package hackathon.display.lcd.rest.resources;

import hackathon.display.lcd.rest.api.Display;
import hackathon.display.lcd.rest.core.DisplayManager;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;

@Path("/display")
public class DisplayResource {
	final private static String LCD_PATH = "/lcd";

	final private DisplayManager _disp;

	public DisplayResource(DisplayManager displaymanager) {
		_disp = displaymanager;
	}

	@GET()
	@Path(LCD_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	@Timed
	@CacheControl(noStore = true)
	// encourage people not to even try to cache this information
	public Response currentDisplay() {
		return Response.ok(_disp.getCurrentDisplay()).build();
	}

	@POST()
	@Path(LCD_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Timed
	@CacheControl(noStore = true)
	public Response updateDisplay(Display display) {
		Response.ResponseBuilder responseBuilder;
		if (display == null) {
			responseBuilder = Response.status(Response.Status.BAD_REQUEST).entity(new Display("input", "must", "contain", "data"));
		} else {
			_disp.updateDisplay(display);
			responseBuilder = Response.ok(_disp.getCurrentDisplay());
		}
		return responseBuilder.build();
	}
}
