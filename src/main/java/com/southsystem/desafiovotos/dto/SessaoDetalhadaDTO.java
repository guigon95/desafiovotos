package com.southsystem.desafiovotos.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.southsystem.desafiovotos.enums.VotoEnum;
import com.southsystem.desafiovotos.model.Sessao;

public class SessaoDetalhadaDTO {

	private Long id;

	private String dataHoraInicio;

	private String dataHoraFim;

	private List<VotoDTO> votos;

	private boolean sessaoAberta;

	private Long quantidadeVotosSim;

	private Long quantidadeVotosNao;

	private PautaDTO pauta;

	public SessaoDetalhadaDTO(Sessao sessao) {
		this.id = sessao.getId();
		this.dataHoraInicio = sessao.getDataHoraInicio().toString();
		this.dataHoraFim = sessao.getDataHoraFim().toString();
		this.votos = new ArrayList<>();
		this.votos.addAll(sessao.getVotos().stream().map(VotoDTO::new).collect(Collectors.toList()));
		this.pauta = new PautaDTO(sessao.getPauta());

		this.sessaoAberta = sessao.sessaoEstaAberta();
		this.quantidadeVotosSim = sessao.getQuantidadeVotosSim();
		this.quantidadeVotosNao = sessao.getQuantidadeVotosNao();
	}

	public Long getId() {
		return id;
	}

	public String getDataHoraInicio() {
		return dataHoraInicio;
	}

	public String getDataHoraFim() {
		return dataHoraFim;
	}

	public List<VotoDTO> getVoto() {
		return votos;
	}

	public PautaDTO getPauta() {
		return pauta;
	}

	public List<VotoDTO> getVotos() {
		return votos;
	}

	public boolean isSessaoAberta() {
		return sessaoAberta;
	}

	public Long getQuantidadeVotosSim() {
		return quantidadeVotosSim;
	}

	public Long getQuantidadeVotosNao() {
		return quantidadeVotosNao;
	}

}
