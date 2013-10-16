package hackathon.display.lcd.rest.core;

import hackathon.display.lcd.rest.api.Display;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.lifecycle.Managed;

public class DisplayManager implements Managed {
	public static Logger				log			= LoggerFactory.getLogger(DisplayManager.class);

	private Display						_disp		= new Display("I2C Display Manager", "display starting", "status ok", " gwre hackathon 2013");
	private LCDFacade					_lcd		= null;

	final private static DisplayManager	INSTANCE	= new DisplayManager();

	public static DisplayManager getInstance() {
		return INSTANCE;
	}

	private void delayMilliseconds(int value) {
		try {
			Thread.sleep(value);
		} catch (InterruptedException ex) {
			log.warn("delayMilliseconds", ex);
		}
	}

	private void dispclear() {
		_disp.clear();
	}

	public Display getCurrentDisplay() {
		synchronized (DisplayManager.class) {
			return _disp;
		}
	}

	public boolean isHealthy() {
		return _lcd != null;
	}

	void lcdbacklightoff() {
		synchronized (_lcd) {
			try {
				if (_lcd != null)
					_lcd.backlight(false);
			} catch (IOException e) {
				log.error("lcdbacklightoff()", e);
			}
		}
	}

	void lcdbacklighton() {
		synchronized (_lcd) {
			try {
				if (_lcd != null)
					_lcd.backlight(true);
			} catch (IOException e) {
				log.error("lcdbacklighton()", e);
			}
		}
	}

	void lcdclear() {
		synchronized (_lcd) {
			try {
				_lcd.clear();
				_lcd.home();
			} catch (IOException e) {
				log.error("clear", e);
			}
		}
	}

	void lcdcursoroff() {
		synchronized (_lcd) {
			try {
				_lcd.cursor(false);
				_lcd.blink(false);
			} catch (IOException e) {
				log.error("cursoroff", e);
			}
		}
	}

	void lcdcursoron() {
		synchronized (_lcd) {
			try {
				_lcd.cursor(true);
				_lcd.blink(true);
			} catch (IOException e) {
				log.error("cursoron", e);
			}
		}
	}

	private void lcdinit() {
		if (_lcd == null) {
			try {
				_lcd = LCDFactory.getImpl();
				_lcd.backlight(true);
				_lcd.autoScroll(false);
				_lcd.home();
				_lcd.clear();
				_lcd.cursor(false);
				_lcd.blink(false);
			} catch (Throwable t) {
				log.error("init failed - setting debug mode", t);
				setDebugMode();
			}
		}
	}

	private void lcdupdateline(int l, String t) {
		synchronized (_lcd) {
			try {
				_lcd.setCursor(0, l);
				_lcd.print(t);
			} catch (IOException e) {
				log.error("update line: " + l, e);
			}
		}
	}

	public void setDebugMode() {
		log.info("display set to debug mode");
		_lcd = new LCDDebugImpl();
	}

	@Override
	public void start() throws Exception {
		lcdinit();
		log.info("lcdinit()");
		updateDisplay(_disp);
		delayMilliseconds(2000);
		// lcdcursoron(); // for debugging purposes
		lcdclear();
		dispclear();
		log.info("displaymanager started");
	}

	@Override
	public void stop() throws Exception {
		lcdclear();
		lcdbacklightoff();
	}

	public void updateDisplay(Display display) {
		log.info("updating: " + String.valueOf(display));
		synchronized (DisplayManager.class) {
			_disp = display;
		}
		lcdclear();
		lcdupdateline(0, _disp.line1());
		lcdupdateline(1, _disp.line2());
		lcdupdateline(2, _disp.line3());
		lcdupdateline(3, _disp.line4());
	}
}
