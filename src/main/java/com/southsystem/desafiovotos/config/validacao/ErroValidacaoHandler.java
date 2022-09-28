package com.southsystem.desafiovotos.config.validacao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroValidacaoHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDTO> handle(MethodArgumentNotValidException exception) {
		List<ErroDeFormularioDTO> dtoList = new ArrayList<ErroDeFormularioDTO>();

		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDeFormularioDTO erro = new ErroDeFormularioDTO(e.getField(), mensagem);

			dtoList.add(erro);

		});

		return dtoList;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ErroDTO handle(IllegalArgumentException exception) {

		ErroDTO dto;

		dto = new ErroDTO(LocalDateTime.now(), exception.getLocalizedMessage());

		return dto;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public ErroDTO handle(Exception exception) {

		ErroDTO dto;

		dto = new ErroDTO(LocalDateTime.now(), exception.getMessage());

		return dto;
	}

}
