package com.southsystem.desafiovotos.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.southsystem.desafiovotos.enums.VotoEnum;
import com.southsystem.desafiovotos.repository.SessaoRepository;

@Entity
@Table
public class Sessao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sessao_id")
	private Long id;

	@Column
	private LocalDateTime dataHoraInicio;

	@Column
	private LocalDateTime dataHoraFim;

	@OneToMany(mappedBy = "sessao", fetch = FetchType.EAGER)
	private List<Voto> votos;

	@ManyToOne
	@JoinColumn(name = "pautaId")
	private Pauta pauta;

	@Column
	private Long quantidadeVotosSim;

	@Column
	private Long quantidadeVotosNao;

	@Column
	private boolean sessaoAberta;

	public Sessao() {
	}

	public Sessao(Pauta pauta, LocalDateTime dataHoraFim) {
		this.pauta = pauta;
		this.dataHoraInicio = LocalDateTime.now();

		this.sessaoAberta = true;

		this.dataHoraFim = dataHoraFim;

		if (dataHoraFim == null)
			this.dataHoraFim = dataHoraInicio.plusMinutes(1);

		this.votos = new ArrayList<>();
	}

	public Sessao(Pauta pauta, LocalDateTime dataHoraFim, LocalDateTime dataHoraInicio) {
		this.pauta = pauta;
		this.dataHoraFim = dataHoraFim;
		this.dataHoraInicio = dataHoraInicio;
		this.sessaoAberta = true;
		this.votos = new ArrayList<>();
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

	public List<Voto> getVotos() {
		return votos;
	}

	public Pauta getPauta() {
		return pauta;
	}

	public boolean ehSessaoAberta() {
		return sessaoAberta;
	}

	public boolean sessaoEstaAberta() {

		if (this.dataHoraFim.isAfter(LocalDateTime.now()))
			return true;

		return false;
	}

	public void setSessaoAberta(boolean sessaoAberta) {
		this.sessaoAberta = sessaoAberta;
	}

	public void fecharSessao() {
		this.sessaoAberta = false;
	}

	public Long getQuantidadeVotosSim() {
		return quantidadeVotosSim;
	}

	public Long getQuantidadeVotosNao() {
		return quantidadeVotosNao;
	}

	public boolean isSessaoAberta() {
		return sessaoAberta;
	}

	public Long setQuantidadeVotosSim(SessaoRepository sessaoRepository) {

		Long quantidadeVotosSim = sessaoRepository.obterQuantidadeVotos(this.getId(), VotoEnum.SIM);

		this.quantidadeVotosSim = quantidadeVotosSim;

		return this.quantidadeVotosSim;
	}

	public Long setQuantidadeVotosNao(SessaoRepository sessaoRepository) {

		Long quantidadeVotosNao = sessaoRepository.obterQuantidadeVotos(this.getId(), VotoEnum.NAO);

		this.quantidadeVotosNao = quantidadeVotosNao;

		return this.quantidadeVotosNao;
	}

}
