package com.southsystem.desafiovotos.enums;

public enum VotoEnum {

	SIM("SIM"), NAO("NAO");

	private String value;

	private VotoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
