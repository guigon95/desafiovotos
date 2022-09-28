package com.southsystem.desafiovotos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.southsystem.desafiovotos.config.validacao.exception.CpfInvalidoException;
import com.southsystem.desafiovotos.config.validacao.exception.VotoException;
import com.southsystem.desafiovotos.dto.UsuarioDTO;
import com.southsystem.desafiovotos.enums.SituacaoCpfEnum;
import com.southsystem.desafiovotos.model.Voto;
import com.southsystem.desafiovotos.repository.VotoRepository;

@Service
public class VotoService {

	@Autowired
	private CpfValidadorService cpfValidador;

	@Autowired
	private VotoRepository votoRepository;

	public void validarVotoNaPauta(Voto voto) {

		Long listaVotosNaPauta = votoRepository.buscarCpfNaPauta(voto.getCpf(), voto.getPautaId());

		if (voto.getCpf().equals(listaVotosNaPauta))
			throw new VotoException("Erro ao votar. Já existe um voto na pauta para o CPF " + voto.getCpf());

	}

	public Voto salvar(Voto voto) {
		return votoRepository.save(voto);
	}

	public Voto votar(Voto voto) {

		if (!voto.sessaoEstaAberta())
			throw new VotoException("Voto não permitido. A Sessão já foi encerrada.");

		validarVotoNaPauta(voto);

		validarCPF(voto);

		this.salvar(voto);

		return voto;
	}

	private void validarCPF(Voto voto) {

		ResponseEntity<UsuarioDTO> resposta = cpfValidador.cpfValido(voto.getCpf().toString());

		if (resposta != null && resposta.getBody() != null) {
			UsuarioDTO dto = resposta.getBody();

			if (SituacaoCpfEnum.UNABLE_TO_VOTE.equals(dto.getStatus()))
				throw new CpfInvalidoException("O CPF " + voto.getCpf() + " não tem permissão para votar");

		} else {
			throw new CpfInvalidoException("O CPF informado não foi encontrado na base");
		}

	}

}
