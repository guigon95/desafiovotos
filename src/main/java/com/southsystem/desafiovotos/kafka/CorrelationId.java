package com.southsystem.desafiovotos.kafka;

import java.util.UUID;

public class CorrelationId {

	private final String id;

	public CorrelationId() {
		id = UUID.randomUUID().toString();
	}

}
