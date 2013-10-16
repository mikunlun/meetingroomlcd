package hackathon.meetingroom.core;

import hackathon.roomavailability.Availability;
import hackathon.roomavailability.RoomAvailabilityService;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.lifecycle.Managed;

public class ExchangeServiceManager implements Managed {
	public static Logger			log	= LoggerFactory.getLogger(ExchangeServiceManager.class);

	private RoomAvailabilityService	_ews;
	private final String			_u;
	private final String			_p;
	private final DateTimeZone		PDT	= DateTimeZone.forID("America/Los_Angeles");

	public ExchangeServiceManager(String user, String password) {
		_u = user;
		_p = password;
	}

	public boolean isHealthly() {
		return (_ews != null);
	}

	@Override
	public void start() throws Exception {
		_ews = new RoomAvailabilityService(_u, _p);
		log.debug("RoomAvailabilityService started: " + _ews);
	}

	@Override
	public void stop() throws Exception {
		_ews = null;
		log.debug("RoomAvailabilityService stopped");
	}

	private DateTime today() {
		return DateTime.now(PDT).withTimeAtStartOfDay();
	}

	private DateTime tomorrow() {
		return DateTime.now(PDT).plusDays(2);
	}

	public Availability getSlots(String room) {
		try {
			Availability avail = _ews.getAvailablity(room, today(), tomorrow());
			log.info("getSlots {} -> {}", room, avail);
			return avail;
		} catch (Exception e) {
			log.debug("microsoft doesn't like you", e);
			return new Availability();
		}
	}

}
