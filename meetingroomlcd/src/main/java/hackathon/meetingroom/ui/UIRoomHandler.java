package hackathon.meetingroom.ui;

import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;
import hackathon.roomavailability.RoomAvailabilityService;
import hackathon.meetingroom.auth.SessionUser;
import hackathon.meetingroom.auth.User;
import hackathon.meetingroom.update.MeetingLCDUpdater;
import hackathon.meetingroom.webclient.api.BookedOrNotResponse;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Path("/room/{id}")
@Produces(MediaType.TEXT_HTML)
public class UIRoomHandler {
    public static Logger                 log                = LoggerFactory.getLogger(UIRoomHandler.class);

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm");
    MeetingLCDUpdater                    _lcd;

    public UIRoomHandler(MeetingLCDUpdater lcd) {
        _lcd = lcd;
    }

    @GET
    public RoomView view(@SessionUser User user, @PathParam("id") String id) {
        return new RoomView(id);
    }

    @POST
    @Path("book")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    @CacheControl(noStore = true)
    public Response book(@Context HttpServletRequest request, @PathParam("id") String id, @FormParam("startTime") String startTime, @FormParam("endTime") String endTime)
            throws Exception {
        try {
            User user = (User) request.getSession().getAttribute("user");
            RoomAvailabilityService ras = new RoomAvailabilityService(user.getName(), user.getPassword());
            DateTime start = getDateTime(startTime);
            DateTime till = getDateTime(endTime);
            log.info("\n***** BOOKING from: {} to: {} *****", start, till);
            ras.bookRoom(id, start, till, "Booked by hackathon");
            _lcd.updateAsync();
            return Response.ok(new BookedOrNotResponse("Booked Successfully")).build();
        } catch (Exception e) {
            return Response.ok(new BookedOrNotResponse("Booking Unsuccessful")).build();
        }
    }

    private DateTime getDateTime(String timeStr) throws ParseException {
        DateTime hoursMinutes = DateTime.parse(timeStr, DateTimeFormat.forPattern("HH:mm"));
        return DateTime.now().withHourOfDay(hoursMinutes.getHourOfDay()).withMinuteOfHour(hoursMinutes.getMinuteOfHour());
    }

}
