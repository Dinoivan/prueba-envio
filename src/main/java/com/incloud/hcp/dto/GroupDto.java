package com.incloud.hcp.dto;

public class GroupDto {
  private String value;
  private String display;
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
public String getDisplay() {
	return display;
}
public void setDisplay(String display) {
	this.display = display;
}

	@Override
	public String toString() {
		return "GroupDto{" +
				"value='" + value + '\'' +
				", display='" + display + '\'' +
				'}';
	}
}
