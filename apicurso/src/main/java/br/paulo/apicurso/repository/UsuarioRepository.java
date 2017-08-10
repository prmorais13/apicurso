package br.paulo.apicurso.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.paulo.apicurso.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	public Optional<Usuario> findByEmail(String email);

}
