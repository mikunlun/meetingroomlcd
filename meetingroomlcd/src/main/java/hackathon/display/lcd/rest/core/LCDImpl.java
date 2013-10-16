package hackathon.display.lcd.rest.core;

import hackathon.display.lcd.i2c.LiquidCrystal_I2C;

import java.io.IOException;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.PinListener;

public class LCDImpl implements LCDFacade {

	private LiquidCrystal_I2C	_lcd;

	public LCDImpl() throws IOException {
		_lcd = new LiquidCrystal_I2C(1, 0x27, 20, 4);
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return _lcd.hashCode();
	}

	/**
	 * @param pin
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#hasPin(com.pi4j.io.gpio.Pin)
	 */
	public boolean hasPin(Pin pin) {
		return _lcd.hasPin(pin);
	}

	/**
	 * @param pin
	 * @param mode
	 * @see com.pi4j.io.gpio.GpioProviderBase#export(com.pi4j.io.gpio.Pin,
	 *      com.pi4j.io.gpio.PinMode)
	 */
	public void export(Pin pin, PinMode mode) {
		_lcd.export(pin, mode);
	}

	/**
	 * @param pin
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#isExported(com.pi4j.io.gpio.Pin)
	 */
	public boolean isExported(Pin pin) {
		return _lcd.isExported(pin);
	}

	/**
	 * @param pin
	 * @see com.pi4j.io.gpio.GpioProviderBase#unexport(com.pi4j.io.gpio.Pin)
	 */
	public void unexport(Pin pin) {
		_lcd.unexport(pin);
	}

	/**
	 * @param pin
	 * @param mode
	 * @see com.pi4j.io.gpio.GpioProviderBase#setMode(com.pi4j.io.gpio.Pin,
	 *      com.pi4j.io.gpio.PinMode)
	 */
	public void setMode(Pin pin, PinMode mode) {
		_lcd.setMode(pin, mode);
	}

	/**
	 * @return
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#getName()
	 */
	public String getName() {
		return _lcd.getName();
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return _lcd.equals(obj);
	}

	/**
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#init()
	 */
	public void init() throws IOException {
		_lcd.init();
	}

	/**
	 * @param b
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#cursor(boolean)
	 */
	public void cursor(boolean b) throws IOException {
		_lcd.cursor(b);
	}

	/**
	 * @param pin
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#getMode(com.pi4j.io.gpio.Pin)
	 */
	public PinMode getMode(Pin pin) {
		return _lcd.getMode(pin);
	}

	/**
	 * @param pin
	 * @param resistance
	 * @see com.pi4j.io.gpio.GpioProviderBase#setPullResistance(com.pi4j.io.gpio.Pin,
	 *      com.pi4j.io.gpio.PinPullResistance)
	 */
	public void setPullResistance(Pin pin, PinPullResistance resistance) {
		_lcd.setPullResistance(pin, resistance);
	}

	/**
	 * @param b
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#blink(boolean)
	 */
	public void blink(boolean b) throws IOException {
		_lcd.blink(b);
	}

	/**
	 * @param b
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#backlight(boolean)
	 */
	public void backlight(boolean b) throws IOException {
		_lcd.backlight(b);
	}

	/**
	 * @param pin
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#getPullResistance(com.pi4j.io.gpio.Pin)
	 */
	public PinPullResistance getPullResistance(Pin pin) {
		return _lcd.getPullResistance(pin);
	}

	/**
	 * @param pin
	 * @param state
	 * @see com.pi4j.io.gpio.GpioProviderBase#setState(com.pi4j.io.gpio.Pin,
	 *      com.pi4j.io.gpio.PinState)
	 */
	public void setState(Pin pin, PinState state) {
		_lcd.setState(pin, state);
	}

	/**
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#clear()
	 */
	public void clear() throws IOException {
		_lcd.clear();
	}

	/**
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#home()
	 */
	public void home() throws IOException {
		_lcd.home();
	}

	/**
	 * @param col
	 * @param row
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#setCursor(int, int)
	 */
	public void setCursor(int col, int row) throws IOException {
		_lcd.setCursor(col, row);
	}

	/**
	 * @param pin
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#getState(com.pi4j.io.gpio.Pin)
	 */
	public PinState getState(Pin pin) {
		return _lcd.getState(pin);
	}

	/**
	 * @param b
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#display(boolean)
	 */
	public void display(boolean b) throws IOException {
		_lcd.display(b);
	}

	/**
	 * @param pin
	 * @param value
	 * @see com.pi4j.io.gpio.GpioProviderBase#setValue(com.pi4j.io.gpio.Pin,
	 *      double)
	 */
	public void setValue(Pin pin, double value) {
		_lcd.setValue(pin, value);
	}

	/**
	 * @param s
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#print(java.lang.String)
	 */
	public void print(String s) throws IOException {
		_lcd.print(s);
	}

	/**
	 * @param c
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#print(char)
	 */
	public void print(char c) throws IOException {
		_lcd.print(c);
	}

	/**
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#scrollDisplayLeft()
	 */
	public void scrollDisplayLeft() throws IOException {
		_lcd.scrollDisplayLeft();
	}

	/**
	 * @param pin
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#getValue(com.pi4j.io.gpio.Pin)
	 */
	public double getValue(Pin pin) {
		return _lcd.getValue(pin);
	}

	/**
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#scrollDisplayRight()
	 */
	public void scrollDisplayRight() throws IOException {
		_lcd.scrollDisplayRight();
	}

	/**
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#leftToRight()
	 */
	public void leftToRight() throws IOException {
		_lcd.leftToRight();
	}

	/**
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#rightToLeft()
	 */
	public void rightToLeft() throws IOException {
		_lcd.rightToLeft();
	}

	/**
	 * @param pin
	 * @param value
	 * @see com.pi4j.io.gpio.GpioProviderBase#setPwm(com.pi4j.io.gpio.Pin, int)
	 */
	public void setPwm(Pin pin, int value) {
		_lcd.setPwm(pin, value);
	}

	/**
	 * @param b
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#autoScroll(boolean)
	 */
	public void autoScroll(boolean b) throws IOException {
		_lcd.autoScroll(b);
	}

	/**
	 * @param pin
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#getPwm(com.pi4j.io.gpio.Pin)
	 */
	public int getPwm(Pin pin) {
		return _lcd.getPwm(pin);
	}

	/**
	 * @param location
	 * @param charmap
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#createChar(int, int[])
	 */
	public void createChar(int location, int[] charmap) throws IOException {
		_lcd.createChar(location, charmap);
	}

	/**
	 * @param pin
	 * @param listener
	 * @see com.pi4j.io.gpio.GpioProviderBase#addListener(com.pi4j.io.gpio.Pin,
	 *      com.pi4j.io.gpio.event.PinListener)
	 */
	public void addListener(Pin pin, PinListener listener) {
		_lcd.addListener(pin, listener);
	}

	/**
	 * @param char_num
	 * @param rows
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#loadCustomCharacter(int,
	 *      int[])
	 */
	public void loadCustomCharacter(int char_num, int[] rows) throws IOException {
		_lcd.loadCustomCharacter(char_num, rows);
	}

	/**
	 * @param pin
	 * @param listener
	 * @see com.pi4j.io.gpio.GpioProviderBase#removeListener(com.pi4j.io.gpio.Pin,
	 *      com.pi4j.io.gpio.event.PinListener)
	 */
	public void removeListener(Pin pin, PinListener listener) {
		_lcd.removeListener(pin, listener);
	}

	/**
	 * 
	 * @see com.pi4j.io.gpio.GpioProviderBase#removeAllListeners()
	 */
	public void removeAllListeners() {
		_lcd.removeAllListeners();
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return _lcd.toString();
	}

	/**
	 * 
	 * @see com.pi4j.io.gpio.GpioProviderBase#shutdown()
	 */
	public void shutdown() {
		_lcd.shutdown();
	}

	/**
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#isShutdown()
	 */
	public boolean isShutdown() {
		return _lcd.isShutdown();
	}

}
