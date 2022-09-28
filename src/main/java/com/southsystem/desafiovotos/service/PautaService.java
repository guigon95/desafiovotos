package com.southsystem.desafiovotos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.southsystem.desafiovotos.config.validacao.exception.ObjetoNaoEncontradoException;
import com.southsystem.desafiovotos.dto.PautaDTO;
import com.southsystem.desafiovotos.dto.PautaForm;
import com.southsystem.desafiovotos.model.Pauta;
import com.southsystem.desafiovotos.repository.PautaRepository;

@Service
public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;

	public Pauta salvar(Pauta pauta) {
		return pautaRepository.save(pauta);
	}

	public Pauta findById(Long id) {
		Optional<Pauta> pauta = pautaRepository.findById(id);

		if (!pauta.isPresent())
			throw new ObjetoNaoEncontradoException("Pauta não encontrada");

		return pauta.get();

	}

	public Page<PautaDTO> pesquisarPautas(PautaForm pautaForm, int page, int size) {

		return pautaRepository.findAll(Example.of(pautaForm.converter()), PageRequest.of(page, size))
				.map(PautaDTO::new);

	}

}
