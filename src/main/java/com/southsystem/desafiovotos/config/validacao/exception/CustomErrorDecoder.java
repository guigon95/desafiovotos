package com.southsystem.desafiovotos.config.validacao.exception;

import org.springframework.http.HttpStatus;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder errorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {

		String requestUrl = response.request().url();
		Response.Body responseBody = response.body();
		HttpStatus responseStatus = HttpStatus.valueOf(response.status());

		if (responseStatus.equals(HttpStatus.NOT_FOUND)) {
			return new CpfInvalidoException("CPF não encontrado na base de validação");
		} else {
			return new Exception("Ocorreu um erro ao validar o CPF");
		}

	}

}
