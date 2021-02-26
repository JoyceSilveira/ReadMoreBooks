package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.Cartao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoDAO extends CrudRepository<Cartao, Integer> {
}
