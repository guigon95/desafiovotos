package com.southsystem.desafiovotos.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.southsystem.desafiovotos.dto.ResultadoDTO;
import com.southsystem.desafiovotos.kafka.KafkaDispatcher;
import com.southsystem.desafiovotos.model.Sessao;

@Service
public class SessaoScheduler {

	@Autowired
	private SessaoService sessaoService;

	@Scheduled(fixedDelay = 60000)
	public void finalizarSessao() {

		List<Sessao> lista = sessaoService.finalizarSessaoAtiva();

		KafkaDispatcher<ResultadoDTO> kafkaDispatcher = new KafkaDispatcher<ResultadoDTO>();

		lista.stream().forEach(sessao -> {

			ResultadoDTO resultadoDTO = new ResultadoDTO(sessao.getId(), sessao.getQuantidadeVotosSim(),
					sessao.getQuantidadeVotosNao());

			try {
				kafkaDispatcher.send("VOTACAO_RESULTADO", sessao.getId().toString(), resultadoDTO);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

		});

	}
}
