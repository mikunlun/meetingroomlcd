package hackathon.display.lcd.rest;

import hackathon.display.lcd.rest.core.DisplayManager;
import hackathon.display.lcd.rest.dropwizard.EmbeddableService;
import hackathon.display.lcd.rest.health.DisplayLCDHealth;
import hackathon.display.lcd.rest.resources.DisplayResource;

import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;

public class DisplayManagerService extends EmbeddableService<Configuration> {

    @Override
    public void initialize(Bootstrap<Configuration> configurationBootstrap) {
        super.initialize(configurationBootstrap);
        configurationBootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public void run(Configuration config, Environment env) throws Exception {
        DisplayManager dmgr = DisplayManager.getInstance();
        env.manage(dmgr);
        env.addResource(new DisplayResource(dmgr));
        env.addHealthCheck(new DisplayLCDHealth(dmgr));
    }

    public static void main(String[] args) throws Exception {
        new DisplayManagerService().run(args);
    }
}
