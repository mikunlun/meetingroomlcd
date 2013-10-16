package hackathon.display.lcd.rest.health;

import hackathon.display.lcd.rest.api.Display;
import hackathon.display.lcd.rest.core.DisplayManager;

import com.yammer.metrics.core.HealthCheck;

public class DisplayLCDHealth extends HealthCheck {
    DisplayManager _dmgr;

    public DisplayLCDHealth(DisplayManager dmgr) {
        super("I2C LCD Display Service");
        _dmgr = dmgr;
    }

    @Override
    protected Result check() throws Exception {
        // TODO: smarter diagnostics
        Display disp = _dmgr.getCurrentDisplay();
        if (_dmgr.isHealthy()) {
            return Result.unhealthy("LCD has problems");
        } else {
            return Result.healthy(String.valueOf(disp));
        }
    }

}
