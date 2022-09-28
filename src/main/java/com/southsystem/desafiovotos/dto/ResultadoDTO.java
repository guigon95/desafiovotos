package com.southsystem.desafiovotos.dto;

public class ResultadoDTO {

	private final Long id;

	private final Long quantidadeVotosSim;

	private final Long quantidadeVotosNao;

	public ResultadoDTO(Long id, Long quantidadeVotosSim, Long quantidadeVotosNao) {
		this.id = id;
		this.quantidadeVotosSim = quantidadeVotosSim;
		this.quantidadeVotosNao = quantidadeVotosNao;
	}

}
