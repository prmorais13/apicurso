package br.paulo.apicurso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.paulo.apicurso.model.Categoria;

public interface Categorias extends JpaRepository<Categoria, Long>{

}
