package com.southsystem.desafiovotos.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.southsystem.desafiovotos.config.validacao.exception.CpfInvalidoException;
import com.southsystem.desafiovotos.config.validacao.exception.VotoException;
import com.southsystem.desafiovotos.dto.UsuarioDTO;
import com.southsystem.desafiovotos.enums.SituacaoCpfEnum;
import com.southsystem.desafiovotos.enums.VotoEnum;
import com.southsystem.desafiovotos.model.Pauta;
import com.southsystem.desafiovotos.model.Sessao;
import com.southsystem.desafiovotos.model.Voto;
import com.southsystem.desafiovotos.repository.VotoRepository;

public class VotoServiceTest {

	@InjectMocks
	private VotoService votoService;

	@Mock
	private VotoRepository votoRepository;

	@Mock
	private CpfValidadorService cpfValidadorService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void votarTest() {

		Pauta pauta = new Pauta("Pauta 1");

		Sessao sessao = new Sessao(pauta, null);

		Voto voto = new Voto(49016482091L, VotoEnum.SIM, sessao);

		when(votoRepository.save(any(Voto.class))).thenReturn(voto);

		UsuarioDTO dto = new UsuarioDTO();
		dto.setStatus(SituacaoCpfEnum.ABLE_TO_VOTE);

		when(cpfValidadorService.cpfValido(anyString())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(dto));

		Voto votoResultado = votoService.votar(voto);

		verify(votoRepository, times(1)).save(any(Voto.class));

		Assertions.assertEquals(voto.getCpf(), votoResultado.getCpf());
		Assertions.assertEquals(voto.getVoto(), votoResultado.getVoto());

		Assertions.assertEquals(voto.getDataHoraInicioSessao(), votoResultado.getDataHoraInicioSessao());
		Assertions.assertEquals(voto.getDataHoraFimSessao(), votoResultado.getDataHoraFimSessao());
		Assertions.assertEquals(voto.getDescricaoPauta(), votoResultado.getDescricaoPauta());

	}

	@Test
	public void votarAposFechamentoDaSessaoTest() {

		VotoException thrown = assertThrows(VotoException.class, () -> {

			Pauta pauta = new Pauta("Pauta 1");

			Sessao sessao = new Sessao(pauta, LocalDateTime.now());

			Voto voto = new Voto(49016482091L, VotoEnum.SIM, sessao);

			votoService.votar(voto);

		});

		assertTrue(thrown.getMessage().contains("Voto não permitido. A Sessão já foi encerrada."));

	}

	@Test
	public void votarComCpfJaUtilizadoTest() {

		when(votoRepository.buscarCpfNaPauta(anyLong(), any())).thenReturn(49016482091L);

		VotoException thrown = assertThrows(VotoException.class, () -> {

			Pauta pauta = new Pauta("Pauta 1");

			Sessao sessao = new Sessao(pauta, null);

			Voto voto = new Voto(49016482091L, VotoEnum.SIM, sessao);

			votoService.votar(voto);

		});

		assertTrue(
				thrown.getMessage().contains("Erro ao votar. Já existe um voto na pauta para o CPF " + 49016482091L));
	}

	@Test
	public void votarComCpfInvalidoTest() {

		CpfInvalidoException thrown = assertThrows(CpfInvalidoException.class, () -> {

			Pauta pauta = new Pauta("Pauta 1");

			Sessao sessao = new Sessao(pauta, null);

			Voto voto = new Voto(49016482091L, VotoEnum.SIM, sessao);

			UsuarioDTO dto = new UsuarioDTO();
			dto.setStatus(SituacaoCpfEnum.UNABLE_TO_VOTE);

			when(cpfValidadorService.cpfValido(anyString())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(dto));

			votoService.votar(voto);

		});

		assertTrue(thrown.getMessage().contains("O CPF 49016482091 não tem permissão para votar"));
	}

	@Test
	public void votarComCpfInexistenteTest() {

		CpfInvalidoException thrown = assertThrows(CpfInvalidoException.class, () -> {

			Pauta pauta = new Pauta("Pauta 1");

			Sessao sessao = new Sessao(pauta, null);

			Voto voto = new Voto(49016482091L, VotoEnum.SIM, sessao);

			UsuarioDTO dto = new UsuarioDTO();
			dto.setStatus(SituacaoCpfEnum.UNABLE_TO_VOTE);

			when(cpfValidadorService.cpfValido(anyString())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

			votoService.votar(voto);

		});

		assertTrue(thrown.getMessage().contains("O CPF informado não foi encontrado na base"));
	}
}
