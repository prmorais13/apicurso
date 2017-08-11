package br.paulo.apicurso.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.paulo.apicurso.model.Lancamento;
import br.paulo.apicurso.model.Pessoa;
import br.paulo.apicurso.repository.Lancamentos;
import br.paulo.apicurso.repository.Pessoas;
import br.paulo.apicurso.repository.filter.LancamentoFilter;
import br.paulo.apicurso.repository.projection.ResumoLancamento;
import br.paulo.apicurso.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private Lancamentos lancamentos;

	@Autowired
	private Pessoas pessoas;
	
	public Page<Lancamento> pesquisa(LancamentoFilter lancamentoFilter, Pageable pageable) {		
		return this.lancamentos.filtrar(lancamentoFilter, pageable);
	}
	
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable) {		
		return this.lancamentos.resumo(filter, pageable);
	}
	
	public Lancamento porCodigo(Long codigo) {
		return this.lancamentos.findOne(codigo);
	}

	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = this.pessoas.findOne(lancamento.getPessoa().getCodigo());
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return this.lancamentos.save(lancamento);
	}
	
	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoEncontrado = this.porCodigo(codigo);
		
		if(lancamentoEncontrado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(lancamento, lancamentoEncontrado, "codigo");
		
		return this.lancamentos.save(lancamentoEncontrado);
	}
	
	public void excluir(Long codigo) {
		this.lancamentos.delete(codigo);
	}
}
