package hackathon.meetingroom;

import hackathon.display.lcd.rest.core.DisplayManager;
import hackathon.display.lcd.rest.dropwizard.EmbeddableService;
import hackathon.display.lcd.rest.health.DisplayLCDHealth;
import hackathon.display.lcd.rest.resources.DisplayResource;
import hackathon.meetingroom.auth.SessionUserProvider;
import hackathon.meetingroom.core.ExchangeServiceManager;
import hackathon.meetingroom.health.ExchangeHealth;
import hackathon.meetingroom.resources.AvailabilityResource;
import hackathon.meetingroom.ui.UILoginHandler;
import hackathon.meetingroom.ui.UIRoomHandler;
import hackathon.meetingroom.update.MeetingLCDUpdater;

import org.eclipse.jetty.server.session.SessionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

public class MeetingRoomService extends EmbeddableService<Configuration> {
	public static Logger	log	= LoggerFactory.getLogger(MeetingRoomService.class);

	final private String	_u;
	final private String	_p;

	public MeetingRoomService() {
		_u = System.getProperty("ews.user");
		_p = System.getProperty("ews.password");
	}

	@Override
	public void initialize(Bootstrap<Configuration> configurationBootstrap) {
		super.initialize(configurationBootstrap);
		configurationBootstrap.addBundle(new ViewBundle());
		configurationBootstrap.addBundle(new AssetsBundle());
	}

	@Override
	public void run(Configuration config, Environment env) throws Exception {
		DisplayManager dmgr = DisplayManager.getInstance();
		String debug = System.getProperty("ews.debug");
		if (debug != null && Boolean.parseBoolean(debug)) {
			dmgr.setDebugMode();
		}
		env.manage(dmgr);
		env.addResource(new DisplayResource(dmgr));
		env.addHealthCheck(new DisplayLCDHealth(dmgr));

		ExchangeServiceManager ews = new ExchangeServiceManager(_u, _p);
		env.manage(ews);
		env.addHealthCheck(new ExchangeHealth(ews));

		MeetingLCDUpdater lcdupdate = new MeetingLCDUpdater();
		env.manage(lcdupdate);

		env.setSessionHandler(new SessionHandler());
		env.scanPackagesForResourcesAndProviders(SessionUserProvider.class);

		env.addResource(new AvailabilityResource());

		env.addResource(new UIRoomHandler(lcdupdate));
		env.addResource(new UILoginHandler());

	}

	public static void main(String[] args) throws Exception {
		new MeetingRoomService().run(args);
	}

}
