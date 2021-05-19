package br.com.fatec.readmorebookstore.facade;

import br.com.fatec.readmorebookstore.dao.*;
import br.com.fatec.readmorebookstore.dominio.*;
import br.com.fatec.readmorebookstore.negocio.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
public class
LivroFacade implements IFacade{

    private final LivroDAO livroDAO;
    private final CategoriaDAO categoriaDAO;
    private final CategoriaLivroDAO categoriaLivroDAO;
    private final LogDesativacaoLivroDAO logDesativacaoLivroDAO;
    private Map<String, CrudRepository> daos;

    @Autowired
    public LivroFacade(LivroDAO livroDAO, CategoriaDAO categoriaDAO, CategoriaLivroDAO categoriaLivroDAO, LogDesativacaoLivroDAO logDesativacaoLivroDAO) {
        this.livroDAO = livroDAO;
        this.categoriaDAO = categoriaDAO;
        this.categoriaLivroDAO = categoriaLivroDAO;
        this.logDesativacaoLivroDAO = logDesativacaoLivroDAO;
        definirDAOS(livroDAO, categoriaDAO, categoriaLivroDAO, logDesativacaoLivroDAO);
    }

    private void definirDAOS(LivroDAO livroDAO, CategoriaDAO categoriaDAO, CategoriaLivroDAO categoriaLivroDAO, LogDesativacaoLivroDAO logDesativacaoLivroDAO){
        daos = new HashMap<>();
        daos.put(Livro.class.getName(), livroDAO);
        daos.put(Categoria.class.getName(), categoriaDAO);
        daos.put(CategoriaLivro.class.getName(), categoriaLivroDAO);
        daos.put(LogDesativacaoLivro.class.getName(), logDesativacaoLivroDAO);
    }

    @Override
    public String cadastrar(AbstractEntidade entidade) {
        CrudRepository dao = daos.get(entidade.getClass().getName());
        dao.save(entidade);
        Livro livro = (Livro) entidade;
        vincularCategorias(livro);
        return null;
    }

    public void vincularCategorias(Livro livro){
        for(int i = 0; i < livro.getCategorias().size(); i++){
            CategoriaLivro categoriaLivro = new CategoriaLivro();
            categoriaLivro.setLivro(livro);
            Categoria categoria = categoriaDAO.findById(livro.getCategorias().get(i)).orElse(null);
            categoriaLivro.setCategoria(categoria);
            alterarDados(categoriaLivro);
        }
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

    public void retirarEstoque(Integer quantidade, Livro livro){
        livro.setEstoque(livro.getEstoque() - quantidade);
        alterarDados(livro);
    }

    public void alterarDados(AbstractEntidade entidade){
        CrudRepository dao = daos.get(entidade.getClass().getName());
        dao.save(entidade);
    }

    public List<Categoria> pegarCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        categoriaDAO.findAll().forEach(categorias::add);
        return categorias;
    }

    public void AtualizarDados(Livro livro, Livro livroForm){
        for(CategoriaLivro categoriaLivro: livro.getCategoriasVinculadas()){
            ExcluirCategoria(categoriaLivro);
        }
        cadastrar(livroForm);
    }

    public void ExcluirCategoria(CategoriaLivro categoriaLivro){
        categoriaLivro.setLivro(null);
        alterarDados(categoriaLivro);
    }

    public void DesativarAtivarLivro(AbstractEntidade entidade){
        LogDesativacaoLivro logDesativacaoLivro = (LogDesativacaoLivro) entidade;
        if(logDesativacaoLivro.getLivro().isAtivo()){
            logDesativacaoLivro.getLivro().setAtivo(false);
        }else {
            logDesativacaoLivro.getLivro().setAtivo(true);
        }
        String nmClasse = entidade.getClass().getName();
        CrudRepository dao = daos.get(nmClasse);
        dao.save(logDesativacaoLivro);
        alterarDados(logDesativacaoLivro.getLivro());
    }

    public void retornarEstoque(Compra compra){
        for(int i = 0; i < compra.getItensVinculados().size(); i++){
            Livro livro = getLivro(compra.getItensVinculados().get(i).getLivro().getId());
            livro.setEstoque(compra.getItensVinculados().get(i).getQuantidade() + livro.getEstoque());
            alterarDados(livro);
        }
    }

    public void retornarEstoqueCarrinho(CompraLivro compraLivro){
        Livro livro = getLivro(compraLivro.getLivro().getId());
        livro.setEstoque(compraLivro.getQuantidade() + livro.getEstoque());
        alterarDados(livro);
    }

}
