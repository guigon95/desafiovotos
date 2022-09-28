package com.southsystem.desafiovotos.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.southsystem.desafiovotos.config.CpfValidadorClientConfig;
import com.southsystem.desafiovotos.dto.UsuarioDTO;

@FeignClient(name = "cpfValidacaoClient", url = "${cpf.validar.url}", configuration = CpfValidadorClientConfig.class)
public interface CpfValidadorService {

	@GetMapping("/{cpf}")
	public ResponseEntity<UsuarioDTO> cpfValido(@PathVariable String cpf);

}
