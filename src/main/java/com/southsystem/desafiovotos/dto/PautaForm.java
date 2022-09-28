package com.southsystem.desafiovotos.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.southsystem.desafiovotos.model.Pauta;

import io.swagger.annotations.ApiModelProperty;

public class PautaForm {

	@ApiModelProperty(value = "Descrição da pauta")
	@NotNull
	@NotEmpty
	private String descricao;

	public Pauta converter() {
		return new Pauta(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
