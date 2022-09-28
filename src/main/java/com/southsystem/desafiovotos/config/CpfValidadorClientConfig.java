package com.southsystem.desafiovotos.config;

import org.springframework.context.annotation.Bean;

import com.southsystem.desafiovotos.config.validacao.exception.CustomErrorDecoder;

import feign.codec.ErrorDecoder;

public class CpfValidadorClientConfig {

	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}

}
