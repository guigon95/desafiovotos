package com.southsystem.desafiovotos.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Pauta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pautaId;

	@Column
	private String descricao;

	@OneToMany(mappedBy = "pauta", fetch = FetchType.EAGER)
	private List<Sessao> sessao;

	public Pauta() {
	}

	public Pauta(String descricao) {
		this.descricao = descricao;
		this.sessao = new ArrayList<>();
	}

	public Long getPautaId() {
		return pautaId;
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Sessao> getSessao() {
		return sessao;
	}

}
