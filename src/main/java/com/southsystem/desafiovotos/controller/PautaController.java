package com.southsystem.desafiovotos.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.southsystem.desafiovotos.dto.DetalharPautaDTO;
import com.southsystem.desafiovotos.dto.PautaDTO;
import com.southsystem.desafiovotos.dto.PautaForm;
import com.southsystem.desafiovotos.model.Pauta;
import com.southsystem.desafiovotos.service.PautaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/pauta/v1")
public class PautaController {

	@Autowired
	private PautaService pautaService;

	@ApiOperation(value = "Cria uma pauta e retorna a pauta criada")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Pauta criada com sucesso"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"), })
	@PostMapping
	public ResponseEntity<DetalharPautaDTO> criarPauta(@RequestBody @Valid PautaForm pautaForm,
			UriComponentsBuilder uriBuilder) {

		Pauta pauta = pautaForm.converter();

		pauta = pautaService.salvar(pauta);

		URI uri = uriBuilder.path("/pauta/v1/{id}").buildAndExpand(pauta.getPautaId()).toUri();

		return ResponseEntity.created(uri).body(new DetalharPautaDTO(pauta));

	}

	@ApiOperation(value = "Retorna uma lista de pautas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna a lista de pautas"),
			@ApiResponse(code = 404, message = "Pauta não encontrada"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"), })
	@GetMapping
	public ResponseEntity<Page<PautaDTO>> obterPautas(@RequestBody PautaForm pautaForm,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {

		Page<PautaDTO> retorno = pautaService.pesquisarPautas(pautaForm, page, size);

		return ResponseEntity.ok(retorno);

	}

	@ApiOperation(value = "Retorna uma pauta por id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna a pauta"),
			@ApiResponse(code = 404, message = "Pauta não encontrada"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"), })
	@GetMapping("/{id}")
	public ResponseEntity<DetalharPautaDTO> detalhar(@PathVariable Long id) {

		Pauta pauta = pautaService.findById(id);

		DetalharPautaDTO dto = new DetalharPautaDTO(pauta);

		return ResponseEntity.ok(dto);

	}

}