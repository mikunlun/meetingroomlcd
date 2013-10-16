package hackathon.meetingroom.resources;

import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import hackathon.roomavailability.Availability;
import hackathon.meetingroom.auth.SessionUser;
import hackathon.meetingroom.auth.User;
import hackathon.meetingroom.core.ExchangeServiceManager;
import hackathon.meetingroom.webclient.api.AvailabilityResponse;
import hackathon.meetingroom.webclient.api.BookingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/meetingrooms")
public class AvailabilityResource {
    public static Logger                 log                               = LoggerFactory.getLogger(AvailabilityResource.class);
    private static final String          MEETINGROOMNAME_AVAILABILITY_PATH = "/{meetingroom}/availability";

    @GET()
    @Path(MEETINGROOMNAME_AVAILABILITY_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    @CacheControl(noStore = true)
    public Response currentSlots(@SessionUser User user, @PathParam("meetingroom") String meetingroom) throws Exception {
      ExchangeServiceManager ews = new ExchangeServiceManager(user.getName(), user.getPassword());
      ews.start();
      try {
        log.info("currentSlots: " + meetingroom);
        try {
          Availability avail = ews.getSlots(meetingroom);
          log.info("avail ews: {}", avail);
          AvailabilityResponse resp = BookingResponse.buildFrom(avail);
          return Response.ok(resp).build();
        } catch (Exception e) {
          log.error("currentSlots()", e);
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } finally {
        ews.stop();
      }


    }

}
