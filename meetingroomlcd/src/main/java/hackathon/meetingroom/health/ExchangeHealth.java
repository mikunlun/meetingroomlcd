package hackathon.meetingroom.health;

import hackathon.meetingroom.core.ExchangeServiceManager;

import com.yammer.metrics.core.HealthCheck;

public class ExchangeHealth extends HealthCheck {

    private final ExchangeServiceManager _esm;

    public ExchangeHealth(ExchangeServiceManager esm) {
        super("Exchange Service Health");
        _esm = esm;
    }

    @Override
    protected Result check() throws Exception {
        // TODO: smarter diagnostics
        if (_esm.isHealthly()) {
            return Result.healthy();
        }
        return Result.unhealthy("Exchange Service Manager has problems");
    }

}
