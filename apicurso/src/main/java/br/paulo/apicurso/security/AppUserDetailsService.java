package br.paulo.apicurso.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.paulo.apicurso.model.Usuario;
import br.paulo.apicurso.repository.UsuarioRepository;

@Component
public class AppUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioEncontrado = this.usuarioRepository.findByEmail(email);
		Usuario usuario = usuarioEncontrado.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorreto!"));
		
		return new UsuarioSistema(usuario, getPermisao(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermisao(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		usuario.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
		return authorities;
	}

}
