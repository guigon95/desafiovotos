package com.southsystem.desafiovotos.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.southsystem.desafiovotos.enums.VotoEnum;
import com.southsystem.desafiovotos.model.Sessao;
import com.southsystem.desafiovotos.model.Voto;
import com.southsystem.desafiovotos.service.SessaoService;

import io.swagger.annotations.ApiModelProperty;

public class VotoForm {

	@ApiModelProperty(value = "CPF do associado")
	@NotNull(message = "O CPF do associado é obrigatório")
	private Long cpf;

	@ApiModelProperty(value = "Voto do associado")
	@NotNull(message = "O voto é obrigatório e deve ser SIM ou NAO")
	@NotEmpty(message = "O voto é obrigatório e deve ser SIM ou NAO")
	private String voto;

	@ApiModelProperty(value = "Id da sessão de votação do associado")
	@NotNull(message = "O id da sessão é obrigatório")
	private Long sessaoId;

	public Long getCpf() {
		return cpf;
	}

	public String getVoto() {
		return voto;
	}

	public Long getSessaoId() {
		return sessaoId;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public void setVoto(String voto) {
		this.voto = voto;
	}

	public void setSessaoId(Long sessaoId) {
		this.sessaoId = sessaoId;
	}

	public Voto converter(SessaoService sessaoService) {

		Sessao sessao = sessaoService.findById(sessaoId);

		Voto votoDO = new Voto(cpf, VotoEnum.valueOf(voto), sessao);

		return votoDO;
	}

}
