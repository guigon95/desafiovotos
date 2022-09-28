package com.southsystem.desafiovotos.dto;

import com.southsystem.desafiovotos.model.Voto;

public class VotoDetalhadoDTO {

	private Long id;

	private Long cpf;

	private String votoEnum;

	private SessaoDTO sessao;

	public VotoDetalhadoDTO(Voto voto) {
		this.id = voto.getId();
		this.cpf = voto.getCpf();
		this.votoEnum = voto.getVoto().toString();
		this.sessao = new SessaoDTO(voto.getSessao());
	}

	public Long getId() {
		return id;
	}

	public Long getCpf() {
		return cpf;
	}

	public String getVotoEnum() {
		return votoEnum;
	}

	public SessaoDTO getSessao() {
		return sessao;
	}

}
