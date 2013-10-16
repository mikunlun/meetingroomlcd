package hackathon.meetingroom.update;

import hackathon.roomavailability.Availability;
import hackathon.display.lcd.rest.api.Display;
import hackathon.display.lcd.rest.core.DisplayManager;
import hackathon.meetingroom.core.ExchangeServiceManager;
import hackathon.meetingroom.lcd.LCDFormatter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.lifecycle.Managed;

public class MeetingLCDUpdater implements Managed {
	public static Logger					log				= LoggerFactory.getLogger(MeetingLCDUpdater.class);

	final private ScheduledExecutorService	_refreshProcess	= Executors.newSingleThreadScheduledExecutor();
	final private ExchangeServiceManager	ews;

	final private String					_room;
	final private String					_u;
	final private String					_p;

	public MeetingLCDUpdater() throws Exception {
		_u = System.getProperty("ews.user");
		_p = System.getProperty("ews.password");
		_room = System.getProperty("ews.room");
		ews = new ExchangeServiceManager(_u, _p);
		ews.start();
	}

	@Override
	public void start() throws Exception {
		_refreshProcess.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				updateLCD();
			}

		}, 0, 15, TimeUnit.SECONDS); // change to 2 mins for non-demo purposes
	}

	@Override
	public void stop() throws Exception {
		_refreshProcess.shutdown();
		ews.stop();
	}

	public void updateAsync() {
		_refreshProcess.schedule(new Runnable() {

			@Override
			public void run() {
				updateLCD();
			}

		}, 8, TimeUnit.SECONDS);

	}

	public void updateLCD() {
		Availability avail = null;
		try {
			avail = ews.getSlots(_room);
		} catch (Throwable t) {
			log.info("update error from ews", t);
			avail = new Availability();
		}
		Display display = LCDFormatter.fromAvailability(avail);
		DisplayManager.getInstance().updateDisplay(display);
	}

}
