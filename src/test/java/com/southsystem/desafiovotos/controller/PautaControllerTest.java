package com.southsystem.desafiovotos.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.southsystem.desafiovotos.dto.PautaDTO;
import com.southsystem.desafiovotos.dto.PautaForm;
import com.southsystem.desafiovotos.model.Pauta;
import com.southsystem.desafiovotos.service.PautaService;

@WebMvcTest(PautaController.class)
public class PautaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PautaService pautaService;

	@InjectMocks
	private ObjectMapper objectMapper;

	@Test
	public void criarPautaTest() throws Exception {

		PautaForm pautaForm = new PautaForm();
		pautaForm.setDescricao("Pauta 1");

		Pauta pauta = new Pauta("Pauta 1");

		when(pautaService.salvar(any(Pauta.class))).thenReturn(pauta);

		this.mockMvc
				.perform(post("/pauta/v1").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(pautaForm)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.descricao", is(pauta.getDescricao())));
	}

	@Test
	public void pautaPesquisarTest() throws Exception {

		PautaDTO pautaDTO = new PautaDTO();
		pautaDTO.setId(1L);
		pautaDTO.setDescricao("Pauta 1");
		
		PautaDTO pautaDTO2 = new PautaDTO();
		pautaDTO2.setId(2L);
		pautaDTO2.setDescricao("Pauta 2");

		PautaForm pautaForm = new PautaForm();

		Page<PautaDTO> pautas = new PageImpl<PautaDTO>(Arrays.asList(pautaDTO, pautaDTO2), PageRequest.of(10, 10), 2);

		when(pautaService.pesquisarPautas(any(PautaForm.class), anyInt(), anyInt())).thenReturn(pautas);

		this.mockMvc.perform(get("/pauta/v1/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pautaForm))).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(2)))
				.andExpect(jsonPath("$.content[0].id").value("1"))
				.andExpect(jsonPath("$.content[0].descricao").value("Pauta 1"))
				.andExpect(jsonPath("$.content[1].id").value("2"))
				.andExpect(jsonPath("$.content[1].descricao").value("Pauta 2"));;
	}

	@Test
	public void pautaDetalharTest() throws Exception {

		PautaForm pautaForm = new PautaForm();
		pautaForm.setDescricao("Pauta 1");

		Pauta pauta = new Pauta("Pauta 1");

		when(pautaService.findById(anyLong())).thenReturn(pauta);

		this.mockMvc.perform(get("/pauta/v1/{id}", "1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.descricao", is(pauta.getDescricao())));
	}
}
