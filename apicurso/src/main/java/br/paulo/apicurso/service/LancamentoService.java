package br.paulo.apicurso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.paulo.apicurso.model.Lancamento;
import br.paulo.apicurso.repository.Lancamentos;

@Service
public class LancamentoService {
	
	@Autowired
	private Lancamentos lancamentos;
	
	public List<Lancamento> buscarTodos(){
		return this.lancamentos.findAll();
	}

}
