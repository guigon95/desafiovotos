package com.southsystem.desafiovotos.dto;

import com.southsystem.desafiovotos.model.Voto;

public class VotoDTO {

	private Long id;

	private Long cpf;

	private String voto;

	public VotoDTO(Voto voto) {
		this.id = voto.getId();
		this.cpf = voto.getCpf();
		this.voto = voto.getVoto().toString();
	}

	public Long getId() {
		return id;
	}

	public Long getCpf() {
		return cpf;
	}

	public String getVoto() {
		return voto;
	}

}
