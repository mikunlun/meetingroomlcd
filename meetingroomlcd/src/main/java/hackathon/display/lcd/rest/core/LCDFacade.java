package hackathon.display.lcd.rest.core;

import java.io.IOException;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.PinListener;

public interface LCDFacade {

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode();

	/**
	 * @param pin
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#hasPin(com.pi4j.io.gpio.Pin)
	 */
	public boolean hasPin(Pin pin);

	/**
	 * @param pin
	 * @param mode
	 * @see com.pi4j.io.gpio.GpioProviderBase#export(com.pi4j.io.gpio.Pin,
	 *      com.pi4j.io.gpio.PinMode)
	 */
	public void export(Pin pin, PinMode mode);

	/**
	 * @param pin
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#isExported(com.pi4j.io.gpio.Pin)
	 */
	public boolean isExported(Pin pin);

	/**
	 * @param pin
	 * @see com.pi4j.io.gpio.GpioProviderBase#unexport(com.pi4j.io.gpio.Pin)
	 */
	public void unexport(Pin pin);

	/**
	 * @param pin
	 * @param mode
	 * @see com.pi4j.io.gpio.GpioProviderBase#setMode(com.pi4j.io.gpio.Pin,
	 *      com.pi4j.io.gpio.PinMode)
	 */
	public void setMode(Pin pin, PinMode mode);

	/**
	 * @return
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#getName()
	 */
	public String getName();

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj);

	/**
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#init()
	 */
	public void init() throws IOException;

	/**
	 * @param b
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#cursor(boolean)
	 */
	public void cursor(boolean b) throws IOException;

	/**
	 * @param pin
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#getMode(com.pi4j.io.gpio.Pin)
	 */
	public PinMode getMode(Pin pin);

	/**
	 * @param pin
	 * @param resistance
	 * @see com.pi4j.io.gpio.GpioProviderBase#setPullResistance(com.pi4j.io.gpio.Pin,
	 *      com.pi4j.io.gpio.PinPullResistance)
	 */
	public void setPullResistance(Pin pin, PinPullResistance resistance);

	/**
	 * @param b
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#blink(boolean)
	 */
	public void blink(boolean b) throws IOException;

	/**
	 * @param b
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#backlight(boolean)
	 */
	public void backlight(boolean b) throws IOException;

	/**
	 * @param pin
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#getPullResistance(com.pi4j.io.gpio.Pin)
	 */
	public PinPullResistance getPullResistance(Pin pin);

	/**
	 * @param pin
	 * @param state
	 * @see com.pi4j.io.gpio.GpioProviderBase#setState(com.pi4j.io.gpio.Pin,
	 *      com.pi4j.io.gpio.PinState)
	 */
	public void setState(Pin pin, PinState state);

	/**
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#clear()
	 */
	public void clear() throws IOException;

	/**
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#home()
	 */
	public void home() throws IOException;

	/**
	 * @param col
	 * @param row
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#setCursor(int, int)
	 */
	public void setCursor(int col, int row) throws IOException;

	/**
	 * @param pin
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#getState(com.pi4j.io.gpio.Pin)
	 */
	public PinState getState(Pin pin);

	/**
	 * @param b
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#display(boolean)
	 */
	public void display(boolean b) throws IOException;

	/**
	 * @param pin
	 * @param value
	 * @see com.pi4j.io.gpio.GpioProviderBase#setValue(com.pi4j.io.gpio.Pin,
	 *      double)
	 */
	public void setValue(Pin pin, double value);

	/**
	 * @param s
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#print(java.lang.String)
	 */
	public void print(String s) throws IOException;

	/**
	 * @param c
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#print(char)
	 */
	public void print(char c) throws IOException;

	/**
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#scrollDisplayLeft()
	 */
	public void scrollDisplayLeft() throws IOException;

	/**
	 * @param pin
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#getValue(com.pi4j.io.gpio.Pin)
	 */
	public double getValue(Pin pin);

	/**
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#scrollDisplayRight()
	 */
	public void scrollDisplayRight() throws IOException;

	/**
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#leftToRight()
	 */
	public void leftToRight() throws IOException;

	/**
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#rightToLeft()
	 */
	public void rightToLeft() throws IOException;

	/**
	 * @param pin
	 * @param value
	 * @see com.pi4j.io.gpio.GpioProviderBase#setPwm(com.pi4j.io.gpio.Pin, int)
	 */
	public void setPwm(Pin pin, int value);

	/**
	 * @param b
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#autoScroll(boolean)
	 */
	public void autoScroll(boolean b) throws IOException;

	/**
	 * @param pin
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#getPwm(com.pi4j.io.gpio.Pin)
	 */
	public int getPwm(Pin pin);

	/**
	 * @param location
	 * @param charmap
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#createChar(int, int[])
	 */
	public void createChar(int location, int[] charmap) throws IOException;

	/**
	 * @param pin
	 * @param listener
	 * @see com.pi4j.io.gpio.GpioProviderBase#addListener(com.pi4j.io.gpio.Pin,
	 *      com.pi4j.io.gpio.event.PinListener)
	 */
	public void addListener(Pin pin, PinListener listener);

	/**
	 * @param char_num
	 * @param rows
	 * @throws IOException
	 * @see hackathon.display.lcd.i2c.LiquidCrystal_I2C#loadCustomCharacter(int,
	 *      int[])
	 */
	public void loadCustomCharacter(int char_num, int[] rows) throws IOException;

	/**
	 * @param pin
	 * @param listener
	 * @see com.pi4j.io.gpio.GpioProviderBase#removeListener(com.pi4j.io.gpio.Pin,
	 *      com.pi4j.io.gpio.event.PinListener)
	 */
	public void removeListener(Pin pin, PinListener listener);

	/**
	 * 
	 * @see com.pi4j.io.gpio.GpioProviderBase#removeAllListeners()
	 */
	public void removeAllListeners();

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString();

	/**
	 * 
	 * @see com.pi4j.io.gpio.GpioProviderBase#shutdown()
	 */
	public void shutdown();

	/**
	 * @return
	 * @see com.pi4j.io.gpio.GpioProviderBase#isShutdown()
	 */
	public boolean isShutdown();
}
