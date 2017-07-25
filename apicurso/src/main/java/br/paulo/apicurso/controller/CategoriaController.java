package br.paulo.apicurso.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.paulo.apicurso.event.RecursoCriadoEvent;
import br.paulo.apicurso.model.Categoria;
import br.paulo.apicurso.repository.Categorias;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private Categorias categorias;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping()
	public List<Categoria> listar() {
		return this.categorias.findAll();
	}

	@PostMapping()
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva =  this.categorias.save(categoria);

		this.publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPorCodigo(@PathVariable Long codigo) {
		Categoria categoriaEncontrada = this.categorias.findOne(codigo);
		return categoriaEncontrada == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoriaEncontrada);
	}
}

