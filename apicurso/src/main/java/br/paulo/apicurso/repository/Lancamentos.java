package br.paulo.apicurso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.paulo.apicurso.model.Lancamento;

public interface Lancamentos extends JpaRepository<Lancamento, Long> {

}
