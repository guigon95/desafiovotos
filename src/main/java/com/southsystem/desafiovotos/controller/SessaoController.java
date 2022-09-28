package com.southsystem.desafiovotos.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.southsystem.desafiovotos.dto.SessaoDetalhadaDTO;
import com.southsystem.desafiovotos.dto.SessaoFiltro;
import com.southsystem.desafiovotos.dto.SessaoForm;
import com.southsystem.desafiovotos.model.Sessao;
import com.southsystem.desafiovotos.service.PautaService;
import com.southsystem.desafiovotos.service.SessaoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/v1/sessao")
public class SessaoController {

	@Autowired
	private PautaService pautaService;

	@Autowired
	private SessaoService sessaoService;

	@ApiOperation(value = "Cria uma sessão e retorna a sessão criada")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Pauta criada com sucesso"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"), })
	@CacheEvict(value = "listaDeSessao", allEntries = true)
	@PostMapping
	public ResponseEntity<SessaoDetalhadaDTO> criarSessao(@RequestBody @Valid SessaoForm sessaoForm,
			UriComponentsBuilder uriBuilder) {

		Sessao sessao = sessaoForm.converter(pautaService);

		sessaoService.salvar(sessao);

		URI uri = uriBuilder.path("/sessao/v1/{id}").buildAndExpand(sessao.getId()).toUri();

		return ResponseEntity.created(uri).body(new SessaoDetalhadaDTO(sessao));
	}

	@ApiOperation(value = "Retorna uma lista de sessão paginada")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna a lista de sessão"),
			@ApiResponse(code = 404, message = "Sessão não encontrada"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"), })
	@Cacheable(value = "listaDeSessao")
	@GetMapping
	public ResponseEntity<Page<SessaoDetalhadaDTO>> obterPautas(@RequestBody SessaoFiltro sessaoFiltro,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {

		Page<SessaoDetalhadaDTO> retorno = sessaoService.pesquisarSessoes(sessaoFiltro, page, size);

		return ResponseEntity.ok(retorno);

	}

}
