package hackathon.display.lcd.rest.dropwizard;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;

/**
 * A Service that can be started, stopped, and asked if running, mainly for use
 * in tests.
 * 
 * Pretty much a copy of the code here:
 * https://github.com/alexspurling/dropwizard
 * -ci/blob/master/src/main/java/org/pio/dropwizard/EmbeddableService.java
 */
public abstract class EmbeddableService<T extends Configuration> extends Service<T> {

	private final EmbeddedServerCommand<T> embeddedServerCommand = new EmbeddedServerCommand<>(this);

	public void startEmbeddedServer(String configFileName) throws Exception {
		// if this fails, it means you overrode the initialize() method in your
		// service, but didn't call super.initialize()
		run(new String[] { "embedded-server", configFileName });
	}

	public void stopEmbeddedServer() throws Exception {
		embeddedServerCommand.stop();
	}

	public boolean isEmbeddedServerRunning() {
		return embeddedServerCommand.isRunning();
	}

	@Override
	public void initialize(Bootstrap<T> bootstrap) {
		bootstrap.addCommand(embeddedServerCommand);
	}

}