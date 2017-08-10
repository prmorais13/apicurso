package br.paulo.apicurso.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GereadorSenha {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
	}

}
