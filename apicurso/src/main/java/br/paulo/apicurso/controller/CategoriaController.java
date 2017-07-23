package br.paulo.apicurso.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

	@PostMapping()
	public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva =  this.categorias.save(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(categoriaSalva.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPorCodigo(@PathVariable Long codigo) {
		Categoria categoriaEncontrada = this.categorias.findOne(codigo);
		return categoriaEncontrada == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoriaEncontrada);
	}
}

