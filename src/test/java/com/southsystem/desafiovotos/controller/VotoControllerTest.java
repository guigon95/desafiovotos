package com.southsystem.desafiovotos.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.southsystem.desafiovotos.dto.VotoForm;
import com.southsystem.desafiovotos.service.VotoService;

@WebMvcTest(VotoController.class)
public class VotoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VotoService votoService;
	
	@InjectMocks
	private ObjectMapper objectMapper;
	
	
	public void votarTest() throws Exception {
		
		VotoForm votoForm = new VotoForm();
		votoForm.setCpf(53781148025L);
		votoForm.setSessaoId(1L);
		votoForm.setVoto("SIM");
		
		this.mockMvc
				.perform(post("/v1/voto").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(votoForm)))
				.andExpect(status().isCreated());	
	}

}
