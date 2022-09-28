package com.southsystem.desafiovotos.dto;

import java.time.LocalDateTime;

import com.southsystem.desafiovotos.model.Sessao;

public class SessaoDTO {

	private Long id;

	private LocalDateTime dataHoraInicio;

	private LocalDateTime dataHoraFim;
	
	private boolean sessaoAberta; 
	
	private Long quantidadeVotosSim;
	
	private Long quantidadeVotosNao;

	public SessaoDTO(Sessao sessao) {
		this.id = sessao.getId();
		this.dataHoraInicio = sessao.getDataHoraInicio();
		this.dataHoraFim = sessao.getDataHoraFim();
		this.sessaoAberta = sessao.sessaoEstaAberta();
		this.quantidadeVotosSim = sessao.getQuantidadeVotosSim();
		this.quantidadeVotosNao = sessao.getQuantidadeVotosNao();
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getDataHoraInicio() {
		return dataHoraInicio;
	}

	public LocalDateTime getDataHoraFim() {
		return dataHoraFim;
	}

}
