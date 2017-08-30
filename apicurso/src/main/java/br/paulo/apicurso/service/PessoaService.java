package br.paulo.apicurso.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.paulo.apicurso.model.Pessoa;
import br.paulo.apicurso.repository.Pessoas;

@Service
public class PessoaService {

	@Autowired
	private Pessoas pessoas;
	
	public Page<Pessoa> pesquisar(String nome, Pageable pageable) {
		return this.pessoas.findByNomeContaining(nome, pageable);
	}

	public Pessoa salvar(Pessoa pessoa) {
		return this.pessoas.save(pessoa);
	}

	public List<Pessoa> buscar() {
		return this.pessoas.findAll();
	}

	public Pessoa porCodigo(Long codigo) {
		return this.pessoas.findOne(codigo);
	}

	public void excluir(Long codigo) {
		this.pessoas.delete(codigo);
	}

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaAtualizada = buscarPessoaPorCodigo(codigo);

		BeanUtils.copyProperties(pessoa, pessoaAtualizada, "codigo");
		return this.pessoas.save(pessoaAtualizada);
	}

	public Pessoa atualizarAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaAtualizada = buscarPessoaPorCodigo(codigo);
		pessoaAtualizada.setAtivo(ativo);

		return this.pessoas.save(pessoaAtualizada);

	}

	private Pessoa buscarPessoaPorCodigo(Long codigo) {
		Pessoa pessoaAtualizada = this.pessoas.findOne(codigo);

		if (pessoaAtualizada == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaAtualizada;
	}

}
