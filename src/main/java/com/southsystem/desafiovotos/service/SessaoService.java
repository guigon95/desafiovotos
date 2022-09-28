package com.southsystem.desafiovotos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.southsystem.desafiovotos.config.validacao.exception.ObjetoNaoEncontradoException;
import com.southsystem.desafiovotos.dto.SessaoDetalhadaDTO;
import com.southsystem.desafiovotos.dto.SessaoFiltro;
import com.southsystem.desafiovotos.model.Sessao;
import com.southsystem.desafiovotos.repository.SessaoRepository;

@Service
public class SessaoService {

	@Autowired
	private SessaoRepository sessaoRepository;

	@Autowired
	private PautaService pautaService;

	public Sessao salvar(Sessao sessao) {

		return sessaoRepository.save(sessao);
	}

	public Sessao findById(Long sessaoId) {

		Optional<Sessao> sessao = sessaoRepository.findById(sessaoId);

		if (!sessao.isPresent())
			throw new ObjetoNaoEncontradoException("Sessão não encontrada");

		return sessao.get();
	}

	public Page<SessaoDetalhadaDTO> pesquisarSessoes(SessaoFiltro sessaoFiltro, int page, int size) {

		Sessao sessao = sessaoFiltro.converter(pautaService);

		return sessaoRepository.findAll(Example.of(sessao), PageRequest.of(page, size)).map(SessaoDetalhadaDTO::new);
	}

	public List<Sessao> finalizarSessaoAtiva() {

		List<Sessao> lista = sessaoRepository.findSessaoAbertaEDataHoraFimMenorQueAtual(LocalDateTime.now());

		lista.stream().forEach(sessao -> {

			fecharSessao(sessao);

		});

		return lista;

	}

	private void fecharSessao(Sessao sessao) {

		sessao.fecharSessao();

		sessao.setQuantidadeVotosSim(sessaoRepository);

		sessao.setQuantidadeVotosNao(sessaoRepository);

		sessaoRepository.save(sessao);

	}

}
