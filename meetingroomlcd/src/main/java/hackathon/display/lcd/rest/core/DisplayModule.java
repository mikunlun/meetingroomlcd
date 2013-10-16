package hackathon.display.lcd.rest.core;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class DisplayModule extends SimpleModule {
	private static final long serialVersionUID = -737530287663865951L;

	public DisplayModule() {
		super("DisplayServiceModule", new Version(0, 0, 7, null, "hackathon.display.lcd", "displaymanager"));
	}

	@Override
	public void setupModule(SetupContext context) {
		super.setupModule(context);
	}
}
