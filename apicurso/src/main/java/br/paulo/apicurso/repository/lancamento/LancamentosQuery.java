package br.paulo.apicurso.repository.lancamento;

import java.util.List;

import br.paulo.apicurso.model.Lancamento;
import br.paulo.apicurso.repository.filter.LancamentoFilter;

public interface LancamentosQuery {
	
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);

}
