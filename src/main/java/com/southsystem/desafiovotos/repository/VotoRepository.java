package com.southsystem.desafiovotos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.southsystem.desafiovotos.model.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

	@Query("SELECT v.cpf FROM Voto v, Sessao s, Pauta p WHERE s.id = v.sessao.id AND p.pautaId = s.pauta.pautaId AND v.cpf = ?1 AND p.pautaId = ?2")
	public Long buscarCpfNaPauta(Long cpf, Long pautaId);
}
