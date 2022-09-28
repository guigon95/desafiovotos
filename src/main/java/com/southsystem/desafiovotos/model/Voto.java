package com.southsystem.desafiovotos.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.southsystem.desafiovotos.enums.VotoEnum;

@Entity
@Table
public class Voto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Long cpf;

	@Enumerated(EnumType.STRING)
	private VotoEnum voto;

	@ManyToOne
	@JoinColumn(name = "sessao_id", nullable = false)
	private Sessao sessao;

	public Voto() {
	}

	public Voto(Long cpf, VotoEnum voto, Sessao sessao) {
		this.cpf = cpf;
		this.voto = voto;
		this.sessao = sessao;
	}

	public Long getId() {
		return id;
	}

	public Long getCpf() {
		return cpf;
	}

	public VotoEnum getVoto() {
		return voto;
	}

	public LocalDateTime getDataHoraFimSessao() {
		return sessao.getDataHoraFim();
	}

	public LocalDateTime getDataHoraInicioSessao() {
		return sessao.getDataHoraInicio();
	}

	public String getDescricaoPauta() {
		return sessao.getPauta().getDescricao();
	}

	public Long getPautaId() {
		return sessao.getPauta().getPautaId();
	}

	public boolean sessaoEstaAberta() {
		return this.sessao.sessaoEstaAberta();
	}

	public Sessao getSessao() {
		return this.getSessao();
	}

}
