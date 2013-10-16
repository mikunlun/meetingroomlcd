package hackathon.display.lcd.rest.api;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Display {
	private String	_l1			= "";
	private String	_l2			= "";
	private String	_l3			= "";
	private String	_l4			= "";
	private boolean	_validate	= true;

	public Display() {
	}

	public Display(@JsonProperty("line1") String line1, @JsonProperty("line2") String line2, @JsonProperty("line3") String line3, @JsonProperty("line4") String line4) {
		_l1 = validateline(line1);
		_l2 = validateline(line2);
		_l3 = validateline(line3);
		_l4 = validateline(line4);
	}

	public Display withValidationOff() {
		_validate = false;
		return this;
	}

	public Display withLine1(String line) {
		_l1 = validateline(line);
		return this;
	}

	public Display withLine2(String line) {
		_l2 = validateline(line);
		return this;
	}

	public Display withLine3(String line) {
		_l3 = validateline(line);
		return this;
	}

	public Display withLine4(String line) {
		_l4 = validateline(line);
		return this;
	}

	private String validateline(String line) {
		if (line == null) {
			throw new IllegalArgumentException("Line cannot be null");
		}
		if (_validate && line.length() > 20) {
			throw new IllegalArgumentException("Line is longer than 20 characters");
		} else if (line.length() > 20) {
			return line.substring(0, 19);
		}
		return line;
	}

	@JsonGetter("line1")
	public String line1() {
		return _l1;
	}

	@JsonGetter("line2")
	public String line2() {
		return _l2;
	}

	@JsonGetter("line3")
	public String line3() {
		return _l3;
	}

	@JsonGetter("line4")
	public String line4() {
		return _l4;
	}

	@JsonSetter("line1")
	public void setline1(String l) {
		_l1 = l;
	}

	@JsonSetter("line2")
	public void setline2(String l) {
		_l2 = l;
	}

	@JsonSetter("line3")
	public void setline3(String l) {
		_l3 = l;
	}

	@JsonSetter("line4")
	public void setline4(String l) {
		_l4 = l;
	}

	public void clear() {
		_l1 = _l2 = _l3 = _l4 = "";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LCD Display:\n1:[").append(_l1).append("]\n2:[").append(_l2).append("]\n3:[").append(_l3).append("]\n4:[").append(_l4).append("]");
		return builder.toString();
	}
}
