package com.southsystem.desafiovotos.dto;

import java.time.LocalDateTime;

import com.southsystem.desafiovotos.model.Pauta;
import com.southsystem.desafiovotos.model.Sessao;
import com.southsystem.desafiovotos.service.PautaService;

import io.swagger.annotations.ApiModelProperty;

public class SessaoForm {

	@ApiModelProperty(value = "Data hora do fim da sessão", example = "yyyy-MM-ddThh:mm:ss")
	private String dataHoraFim;

	@ApiModelProperty(value = "Id da pauta associada", required = true)
	private Long pautaId;

	public String getDataHoraFim() {
		return dataHoraFim;
	}

	public Long getPautaId() {
		return pautaId;
	}

	public void setDataHoraFim(String dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public void setPautaId(Long pautaId) {
		this.pautaId = pautaId;
	}

	public Sessao converter(PautaService pautaService) {
		Pauta pauta = pautaService.findById(pautaId);
		
		LocalDateTime dtHoraFim = null;
		if(dataHoraFim != null)
			dtHoraFim = LocalDateTime.parse(dataHoraFim);

		Sessao sessao = new Sessao(pauta, dtHoraFim);

		return sessao;
	}

}
