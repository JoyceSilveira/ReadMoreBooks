package br.com.fatec.readmorebookstore.facade;

import br.com.fatec.readmorebookstore.dao.*;
import br.com.fatec.readmorebookstore.dominio.*;
import br.com.fatec.readmorebookstore.negocio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;

import java.util.*;

@Log4j2
@Service
public class CompraFacade implements IFacade {

    private final CompraDAO compraDAO;
    private final CompraLivroDAO compraLivroDAO;
    private Map<String, CrudRepository> daos;

    @Autowired
    public CompraFacade(CompraDAO compraDAO, CompraLivroDAO compraLivroDAO){
        this.compraDAO = compraDAO;
        this.compraLivroDAO = compraLivroDAO;
        definirDAOS(compraDAO, compraLivroDAO);
    }

    private void definirDAOS(CompraDAO compraDAO, CompraLivroDAO compraLivroDAO){
        daos = new HashMap<>();
        daos.put(Compra.class.getName(), compraDAO);
        daos.put(CompraLivro.class.getName(), compraLivroDAO);
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

    public void cadastrarItem(AbstractEntidade entidade){
        CrudRepository dao = daos.get(entidade.getClass().getName());
        dao.save(entidade);
    }
}
