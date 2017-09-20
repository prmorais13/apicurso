package br.paulo.apicurso.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
		this.validarPessoa(lancamento);
		return this.lancamentos.save(lancamento);
	}
	
	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = this.buscarLancamentoExistente(codigo);
		
		if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			this.validarPessoa(lancamento);
		}
		
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
		
		return this.lancamentos.save(lancamentoSalvo);
	}
	
	public void excluir(Long codigo) {
		Lancamento lancamentoSalvo = this.buscarLancamentoExistente(codigo);
		this.lancamentos.delete(lancamentoSalvo.getCodigo());
	}
	
	private void validarPessoa(Lancamento lancamento) {
		Pessoa pessoa = null;
		if (lancamento.getPessoa().getCodigo() != null) {
			pessoa = this.pessoas.findOne(lancamento.getPessoa().getCodigo());
		}
		
		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}
	
	private Lancamento buscarLancamentoExistente(Long codigo) {
		Lancamento lancamentoSalvo = this.lancamentos.findOne(codigo);
		if (lancamentoSalvo == null) {
			throw new IllegalArgumentException();
		}
		return lancamentoSalvo;
	}
	
}
