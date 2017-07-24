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
import br.paulo.apicurso.model.Pessoa;
import br.paulo.apicurso.repository.Pessoas;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private Pessoas pessoas;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Pessoa> listar() {
		return this.pessoas.findAll();
	}

	@PostMapping()
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva =  this.pessoas.save(pessoa);
		
		this.publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPorCodigo(@PathVariable Long codigo) {
		Pessoa pessoaEncontrada = this.pessoas.findOne(codigo);
		return pessoaEncontrada == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(pessoaEncontrada);
	}
}
