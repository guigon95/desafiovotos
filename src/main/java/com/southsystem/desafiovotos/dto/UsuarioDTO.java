package com.southsystem.desafiovotos.dto;

import com.southsystem.desafiovotos.enums.SituacaoCpfEnum;

public class UsuarioDTO {

	private SituacaoCpfEnum status;

	public SituacaoCpfEnum getStatus() {
		return status;
	}

	public void setStatus(SituacaoCpfEnum status) {
		this.status = status;
	}

}
