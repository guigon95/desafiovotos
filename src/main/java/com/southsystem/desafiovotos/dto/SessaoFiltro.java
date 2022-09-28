package com.southsystem.desafiovotos.dto;

import java.time.LocalDateTime;

import com.southsystem.desafiovotos.model.Pauta;
import com.southsystem.desafiovotos.model.Sessao;
import com.southsystem.desafiovotos.service.PautaService;

import io.swagger.annotations.ApiModelProperty;

public class SessaoFiltro {

	@ApiModelProperty(value = "Data hora do início da sessão")
	private LocalDateTime dataHoraInicio;

	@ApiModelProperty(value = "Data hora do fim da sessão")
	private LocalDateTime dataHoraFim;

	@ApiModelProperty(value = "Id da pauta relacionada com a sessão")
	private Long pautaId;

	private boolean sessaoAberta;

	private Long quantidadeVotoSim;

	private Long quantidadeVotoNao;

	public LocalDateTime getDataHoraInicio() {
		return dataHoraInicio;
	}

	public LocalDateTime getDataHoraFim() {
		return dataHoraFim;
	}

	public Long getPautaId() {
		return pautaId;
	}

	public boolean getSessaoAberta() {
		return sessaoAberta;
	}

	public Long getQuantidadeVotoSim() {
		return quantidadeVotoSim;
	}

	public Long getQuantidadeVotoNao() {
		return quantidadeVotoNao;
	}

	public Sessao converter(PautaService pautaService) {

		Pauta pauta = null;

		if (pautaId != null)
			pauta = pautaService.findById(pautaId);

		Sessao sessao = new Sessao(pauta, dataHoraFim, dataHoraInicio);
		sessao.setSessaoAberta(this.sessaoAberta);

		return sessao;
	}

}
