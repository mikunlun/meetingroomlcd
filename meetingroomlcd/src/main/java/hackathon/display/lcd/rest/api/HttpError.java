package hackathon.display.lcd.rest.api;

public class HttpError {
	public final String message;
	public final Display example;

	public HttpError(String message, Display example) {
		this.message = message;
		this.example = example;
	}
}
