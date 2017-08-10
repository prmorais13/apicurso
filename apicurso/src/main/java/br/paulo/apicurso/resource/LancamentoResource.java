package br.paulo.apicurso.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.paulo.apicurso.event.RecursoCriadoEvent;
import br.paulo.apicurso.exceptionhandler.ApicursoExceptionHandler.Erro;
import br.paulo.apicurso.model.Lancamento;
import br.paulo.apicurso.repository.filter.LancamentoFilter;
import br.paulo.apicurso.service.LancamentoService;
import br.paulo.apicurso.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoService lancamentoService;
	
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	//busca lançamentos de acordo com os filtros informados na url
	@GetMapping
	public Page<Lancamento> listar(LancamentoFilter filter, Pageable pageable){
		return this.lancamentoService.pesquisa(filter, pageable);
	}
	
	//Busca lançamentos pelo código
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPorCodigo(@PathVariable Long codigo) {
		Lancamento lancamentoEncontrado = this.lancamentoService.porCodigo(codigo);
		
		if(lancamentoEncontrado == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(lancamentoEncontrado);
		//return lancamentoEncontrado == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(lancamentoEncontrado);
	}
	
	//Adicionar lançamentos
	@PostMapping
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = this.lancamentoService.salvar(lancamento);
		
		this.publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	//Atualiza lançamentos
	@PutMapping("/{codigo}")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo, @Valid @RequestBody Lancamento lancamento) {
		return ResponseEntity.ok(this.lancamentoService.atualizar(codigo, lancamento));
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		this.lancamentoService.excluir(codigo);
	}
	
	//Tratamento de exceção
	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
		String mensagemUsuario = this.messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
	

}
