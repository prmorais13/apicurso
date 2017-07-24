package br.paulo.apicurso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.paulo.apicurso.model.Pessoa;

public interface Pessoas extends JpaRepository<Pessoa, Long> {

}
