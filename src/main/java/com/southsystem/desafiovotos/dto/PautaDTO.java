package com.southsystem.desafiovotos.dto;

import com.southsystem.desafiovotos.model.Pauta;

public class PautaDTO {

	private Long id;

	private String descricao;

	public PautaDTO() {
	}

	public PautaDTO(Pauta pauta) {
		this.id = pauta.getPautaId();
		this.descricao = pauta.getDescricao();
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
