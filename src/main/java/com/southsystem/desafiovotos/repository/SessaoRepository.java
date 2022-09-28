package com.southsystem.desafiovotos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.southsystem.desafiovotos.enums.VotoEnum;
import com.southsystem.desafiovotos.model.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    @Query("select s from Sessao s where s.sessaoAberta = true and s.dataHoraFim < ?1")
	public List<Sessao> findSessaoAbertaEDataHoraFimMenorQueAtual(LocalDateTime localDateTime);

    @Query("SELECT count(*) FROM Voto v, Sessao s where s.id = v.sessao.id and s.id = ?1 and v.voto = ?2")
	public Long obterQuantidadeVotos(Long id, VotoEnum voto);

}
