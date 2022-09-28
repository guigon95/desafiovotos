package com.southsystem.desafiovotos.config.validacao;

import java.time.LocalDateTime;

public class ErroDTO {

	private LocalDateTime timestamp;
	private String erro;

	public ErroDTO(LocalDateTime timestamp, String erro) {
		super();
		this.timestamp = timestamp;
		this.erro = erro;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getErro() {
		return erro;
	}

}
