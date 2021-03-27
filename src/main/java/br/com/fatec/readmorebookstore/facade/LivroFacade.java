package br.com.fatec.readmorebookstore.facade;

import br.com.fatec.readmorebookstore.dao.*;
import br.com.fatec.readmorebookstore.dominio.*;
import br.com.fatec.readmorebookstore.negocio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LivroFacade implements IFacade{

    private final LivroDAO livroDAO;
    private Map<String, CrudRepository> daos;

    @Autowired
    public LivroFacade(LivroDAO livroDAO) {
        this.livroDAO = livroDAO;
        definirDAOS(livroDAO);
    }

    private void definirDAOS(LivroDAO livroDAO){
        daos = new HashMap<>();
        daos.put(Livro.class.getName(), livroDAO);
    }

    @Override
    public String cadastrar(AbstractEntidade entidade) {
        return null;
    }

    @Override
    public String excluir(AbstractEntidade entidade) {
        return null;
    }

    @Override
    public String alterar(AbstractEntidade entidade) {
        return null;
    }

    @Override
    public List<AbstractEntidade> consultar(AbstractEntidade entidade) {
        return null;
    }

    public Livro getLivro(Integer id) {
        Livro livro = livroDAO.findById(id).orElse(null);
        return livro;
    }

    public List<Livro> listarTodos() {
        List<Livro> livros = new ArrayList<>();
        livroDAO.findAll().forEach(livros::add);
        return livros;
    }
}
