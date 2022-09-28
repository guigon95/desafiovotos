package com.southsystem.desafiovotos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southsystem.desafiovotos.model.Pauta;
import com.southsystem.desafiovotos.model.Voto;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

	// ("SELECT v FROM Voto v, Sessao s WHERE v.cpf = ?1 and s.")
	//public Optional<Voto> validaVotoNaPauta(Long cpf);

}
