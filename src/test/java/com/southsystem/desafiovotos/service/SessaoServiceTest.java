package com.southsystem.desafiovotos.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.southsystem.desafiovotos.dto.SessaoDTO;
import com.southsystem.desafiovotos.dto.SessaoDetalhadaDTO;
import com.southsystem.desafiovotos.dto.SessaoFiltro;
import com.southsystem.desafiovotos.dto.SessaoForm;
import com.southsystem.desafiovotos.model.Pauta;
import com.southsystem.desafiovotos.model.Sessao;
import com.southsystem.desafiovotos.repository.SessaoRepository;

public class SessaoServiceTest {

	@InjectMocks
	private SessaoService sessaoService;

	@Mock
	private SessaoRepository sessaoRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void criarSessaoTest() {

		String descricao = "Pauta Teste";

		Pauta pauta = new Pauta(descricao);

		LocalDateTime horaFim = LocalDateTime.now().plusMinutes(1);

		Sessao sessao = new Sessao(pauta, horaFim);
		LocalDateTime dataHoraInicio = sessao.getDataHoraInicio();

		when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessao);

		Sessao sessaoTest = sessaoService.salvar(sessao);

		verify(sessaoRepository, times(1)).save(any(Sessao.class));

		Assertions.assertTrue(sessao.sessaoEstaAberta());

		Assertions.assertEquals(dataHoraInicio, sessaoTest.getDataHoraInicio());
		Assertions.assertEquals(horaFim, sessaoTest.getDataHoraFim());
		Assertions.assertEquals(descricao, sessao.getPauta().getDescricao());

	}

	@Test
	public void pesquisarSessoesTest() {

		Pauta pauta1 = new Pauta("Pauta 1");

		SessaoFiltro sessaoFiltro = new SessaoFiltro();
		
		LocalDateTime now = LocalDateTime.now();

		List<Sessao> listaSessoes = Arrays.asList(new Sessao(pauta1, now),
				new Sessao(pauta1, now));

		Page<Sessao> pautas = new PageImpl<Sessao>(listaSessoes, PageRequest.of(10, 10), 2);

		when(sessaoRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(pautas);

		Page<SessaoDetalhadaDTO> page = sessaoService.pesquisarSessoes(sessaoFiltro, 10, 10);

		Sessao sessao = listaSessoes.get(0);
		SessaoDetalhadaDTO sessaoDTO = page.get().collect(Collectors.toList()).get(0);

		Assertions.assertEquals(sessao.getDataHoraInicio().toString(), sessaoDTO.getDataHoraInicio());
		Assertions.assertEquals(sessao.getDataHoraFim().toString(), sessaoDTO.getDataHoraFim());
		Assertions.assertEquals(sessao.getPauta().getDescricao(), sessaoDTO.getPauta().getDescricao());
	}

	@Test
	public void findByIdTest() {

		String descricao = "Pauta 1";

		Pauta pauta = new Pauta(descricao);

		LocalDateTime horaFim = LocalDateTime.now().plusMinutes(1);

		Sessao sessao = new Sessao(pauta, horaFim);

		when(sessaoRepository.findById(anyLong())).thenReturn(Optional.of(sessao));

		Sessao sessaoRetornada = sessaoService.findById(1L);

		Assertions.assertEquals(pauta.getDescricao(), sessaoRetornada.getPauta().getDescricao());
		Assertions.assertEquals(horaFim, sessaoRetornada.getDataHoraFim());

	}

	@Test
	public void findByIdNaoEncontradoTest() {

		ObjetoNaoEncontradoException thrown = assertThrows(ObjetoNaoEncontradoException.class, () -> {

			Sessao sessaoRetornada = sessaoService.findById(1L);

		});

		assertTrue(thrown.getMessage().contains("Sessão não encontrada"));

	}

	@Test
	public void sessaoEstaAbertaTest() {

		String descricao = "Pauta 1";

		Pauta pauta = new Pauta(descricao);

		LocalDateTime horaFim = LocalDateTime.now().plusMinutes(2);

		Sessao sessao = new Sessao(pauta, horaFim);

		when(sessaoRepository.findById(anyLong())).thenReturn(Optional.of(sessao));

		Sessao sessaoRetornada = sessaoService.findById(1L);

		Assertions.assertTrue(sessaoRetornada.sessaoEstaAberta());

	}

	@Test
	public void sessaoEstaFechada() {

		String descricao = "Pauta 1";

		Pauta pauta = new Pauta(descricao);

		LocalDateTime horaFim = LocalDateTime.now();

		Sessao sessao = new Sessao(pauta, horaFim);

		when(sessaoRepository.findById(anyLong())).thenReturn(Optional.of(sessao));

		Sessao sessaoRetornada = sessaoService.findById(1L);

		Assertions.assertFalse(sessaoRetornada.sessaoEstaAberta());

	}
}
