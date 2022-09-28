package com.southsystem.desafiovotos.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.southsystem.desafiovotos.config.validacao.exception.ObjetoNaoEncontradoException;
import com.southsystem.desafiovotos.dto.PautaDTO;
import com.southsystem.desafiovotos.dto.PautaForm;
import com.southsystem.desafiovotos.model.Pauta;
import com.southsystem.desafiovotos.repository.PautaRepository;

public class PautaServiceTest {

	@InjectMocks
	private PautaService pautaService;

	@Mock
	private PautaRepository pautaRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void criarPautaTest() {

		String descricao = "Pauta Teste";

		Pauta pauta = new Pauta(descricao);

		when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);

		Pauta pautaTest = pautaService.salvar(pauta);

		verify(pautaRepository, times(1)).save(any(Pauta.class));

		Assertions.assertEquals(descricao, pautaTest.getDescricao());

	}

	@Test
	public void pesquisarPautasTest() {

		PautaForm pautaForm = new PautaForm();

		List<Pauta> listaPautas = Arrays.asList(new Pauta("Pauta 3"), new Pauta("Pauta 4"));

		Page<Pauta> pautas = new PageImpl<Pauta>(listaPautas, PageRequest.of(10, 10), 2);

		when(pautaRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(pautas);

		Page<PautaDTO> page = pautaService.pesquisarPautas(pautaForm, 10, 10);

		Pauta pauta = listaPautas.get(0);
		PautaDTO pautaDTO = page.get().toList().get(0);

		Assertions.assertEquals(pauta.getDescricao(), pautaDTO.getDescricao());
	}

	@Test
	public void findByIdTest() {

		String descricao = "Pauta 2";

		Pauta pauta = new Pauta(descricao);

		when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(pauta));

		Pauta pautaRetornada = pautaService.findById(1L);

		Assertions.assertEquals(descricao, pautaRetornada.getDescricao());

	}

	@Test
	public void findByIdNaoEncontradoTest() {

		ObjetoNaoEncontradoException thrown = assertThrows(ObjetoNaoEncontradoException.class, () -> {

			Pauta pautaRetornada = pautaService.findById(1L);

		});

		assertTrue(thrown.getMessage().contains("Pauta não encontrada"));

	}

}
