package com.octank.coveragesvc.services;

public class Coverage {

	String code;
	String value;
	public Coverage(String code, String value) {
		super();
		this.code = code;
		this.value = value;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
