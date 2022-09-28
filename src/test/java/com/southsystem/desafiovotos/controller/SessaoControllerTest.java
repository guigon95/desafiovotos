package com.southsystem.desafiovotos.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.southsystem.desafiovotos.dto.SessaoDetalhadaDTO;
import com.southsystem.desafiovotos.dto.SessaoFiltro;
import com.southsystem.desafiovotos.dto.SessaoForm;
import com.southsystem.desafiovotos.model.Pauta;
import com.southsystem.desafiovotos.model.Sessao;
import com.southsystem.desafiovotos.repository.SessaoRepository;
import com.southsystem.desafiovotos.service.PautaService;
import com.southsystem.desafiovotos.service.SessaoService;

@WebMvcTest(SessaoController.class)
public class SessaoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SessaoService sessaoService;

	@MockBean
	private PautaService pautaService;
	
	@MockBean
	private SessaoRepository sessaoRepository;

	@InjectMocks
	private ObjectMapper objectMapper;

	@Captor
	ArgumentCaptor<Sessao> sessaoCaptor;

	@Test
	public void criarSessaoTest() throws Exception {

		String dataHoraFim = "2022-09-23T19:30:01";

		SessaoForm sessaoForm = new SessaoForm();
		sessaoForm.setPautaId(1L);

		sessaoForm.setDataHoraFim(dataHoraFim);

		Pauta pauta = new Pauta("Pauta 1");

		Sessao sessao = new Sessao(pauta, LocalDateTime.parse(dataHoraFim));

		when(pautaService.findById(anyLong())).thenReturn(pauta);

		when(sessaoService.salvar(any(Sessao.class))).thenReturn(sessao);

		
		MvcResult mvcResult = this.mockMvc
				.perform(post("/sessao/v1").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(sessaoForm)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.dataHoraFim").value(dataHoraFim))
				.andExpect(jsonPath("$.pauta.descricao").value("Pauta 1"))
				.andReturn();
		
		verify(sessaoService).salvar(sessaoCaptor.capture());

		String horaInicio = sessaoCaptor.getValue().getDataHoraInicio().toString();

		 String dataHoraInicio = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("$.dataHoraInicio");
		
		Assertions.assertEquals(horaInicio, dataHoraInicio);
	}
	
	@Test
	public void sessaoPesquisarTest() throws Exception {

		Pauta pauta = new Pauta("Pauta 1");
		Pauta pauta2 = new Pauta("Pauta 2");

		
		String dataHoraFim = "2022-09-23T19:30:01";
		Sessao sessao = new Sessao(pauta, LocalDateTime.parse(dataHoraFim));
		
		String dataHoraFim2 = "2022-09-23T20:30:01";
		Sessao sessao2 = new Sessao(pauta2, LocalDateTime.parse(dataHoraFim2));
		
		
		Page<SessaoDetalhadaDTO> sessoes = new PageImpl<SessaoDetalhadaDTO>(Arrays.asList(new SessaoDetalhadaDTO(sessao), new SessaoDetalhadaDTO(sessao2)), PageRequest.of(10, 10), 2);

		when(sessaoService.pesquisarSessoes(any(SessaoFiltro.class), anyInt(), anyInt())).thenReturn(sessoes);

		this.mockMvc.perform(get("/sessao/v1/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new SessaoFiltro()))).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(2)))
				.andExpect(jsonPath("$.content[0].dataHoraInicio", is(sessao.getDataHoraInicio().toString())))
				.andExpect(jsonPath("$.content[0].dataHoraFim").value(dataHoraFim))
				.andExpect(jsonPath("$.content[0].pauta.descricao").value("Pauta 1"))
				.andExpect(jsonPath("$.content[1].dataHoraInicio", is(sessao2.getDataHoraInicio().toString())))
				.andExpect(jsonPath("$.content[1].dataHoraFim").value(dataHoraFim2))
				.andExpect(jsonPath("$.content[1].pauta.descricao").value("Pauta 2"));
		
		
	}

}
