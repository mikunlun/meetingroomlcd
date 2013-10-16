package hackathon.display.lcd.rest.core;

import java.io.IOException;

public class LCDFactory {

	public static LCDFacade getImpl() {
		try {
			return new LCDImpl();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
