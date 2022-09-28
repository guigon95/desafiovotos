package com.southsystem.desafiovotos.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.southsystem.desafiovotos.dto.VotoDTO;
import com.southsystem.desafiovotos.dto.VotoForm;
import com.southsystem.desafiovotos.model.Voto;
import com.southsystem.desafiovotos.service.SessaoService;
import com.southsystem.desafiovotos.service.VotoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/v1/voto")
public class VotoController {

	@Autowired
	private VotoService votoService;

	@Autowired
	private SessaoService sessaoService;

	
	@ApiOperation(value = "Armazena um voto e retorna-o")
	@ApiResponses(value = {
	    @ApiResponse(code = 201, message = "Voto armazenado com sucesso"),
	    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@PostMapping
	public ResponseEntity<VotoDTO> votar(@RequestBody @Valid VotoForm votoForm, UriComponentsBuilder uriBuilder) {

		Voto voto = votoForm.converter(sessaoService);

		votoService.votar(voto);

		URI uri = uriBuilder.path("/voto/v1/{id}").buildAndExpand(voto.getId()).toUri();

		return ResponseEntity.created(uri).body(new VotoDTO(voto));

	}
}
