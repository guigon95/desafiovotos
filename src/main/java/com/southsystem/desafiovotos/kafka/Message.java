package com.southsystem.desafiovotos.kafka;

public class Message<T> {

	private CorrelationId id;
	private Object payload;

	public Message(CorrelationId id, Object payload) {
		this.id = id;
		this.payload = payload;
	}

	@Override
	public String toString() {

		return "Message ( id = " + id + ", payload = " + payload + ")";
	}

	public CorrelationId getId() {
		return id;
	}

	public Object getPayload() {
		return payload;
	}

}
