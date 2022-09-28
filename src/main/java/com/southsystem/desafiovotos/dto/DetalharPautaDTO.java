package com.southsystem.desafiovotos.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.southsystem.desafiovotos.model.Pauta;

public class DetalharPautaDTO {

	private Long pautaId;

	private String descricao;

	private List<SessaoDetalhadaDTO> sessoes;

	public DetalharPautaDTO(Pauta pauta) {
		this.pautaId = pauta.getPautaId();
		this.descricao = pauta.getDescricao();
		this.sessoes = new ArrayList<>();
		this.sessoes.addAll(pauta.getSessao().stream().map(SessaoDetalhadaDTO::new).collect(Collectors.toList()));

	}

	public Long getPautaId() {
		return pautaId;
	}

	public String getDescricao() {
		return descricao;
	}

	public List<SessaoDetalhadaDTO> getSessoes() {
		return sessoes;
	}

}
