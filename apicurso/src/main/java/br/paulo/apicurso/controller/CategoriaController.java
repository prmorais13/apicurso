package br.paulo.apicurso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.paulo.apicurso.model.Categoria;
import br.paulo.apicurso.repository.Categorias;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private Categorias categorias;
	
	@GetMapping()
	public List<Categoria> listar() {
		return this.categorias.findAll();
	}
	
	/*public ResponseEntity<?> listar(){
		List<Categoria> categorias = this.categorias.findAll();
		return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.notFound().build();
	}*/

}
