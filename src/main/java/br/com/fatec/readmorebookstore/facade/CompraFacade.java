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
    private final CupomDAO cupomDAO;
    private final CartaoDAO cartaoDAO;
    private final CompraCartaoDAO compraCartaoDAO;
    private Map<String, CrudRepository> daos;

    @Autowired
    public CompraFacade(CompraDAO compraDAO, CompraLivroDAO compraLivroDAO, CupomDAO cupomDAO, CartaoDAO cartaoDAO, CompraCartaoDAO compraCartaoDAO){
        this.compraDAO = compraDAO;
        this.compraLivroDAO = compraLivroDAO;
        this.cupomDAO = cupomDAO;
        this.cartaoDAO = cartaoDAO;
        this.compraCartaoDAO = compraCartaoDAO;
        definirDAOS(compraDAO, compraLivroDAO, cupomDAO, cartaoDAO, compraCartaoDAO);
    }

    private void definirDAOS(CompraDAO compraDAO, CompraLivroDAO compraLivroDAO, CupomDAO cupomDAO, CartaoDAO cartaoDAO, CompraCartaoDAO compraCartaoDAO){
        daos = new HashMap<>();
        daos.put(Compra.class.getName(), compraDAO);
        daos.put(CompraLivro.class.getName(), compraLivroDAO);
        daos.put(Cupom.class.getName(), cupomDAO);
        daos.put(Cartao.class.getName(), cartaoDAO);
        daos.put(CompraCartao.class.getName(), compraCartaoDAO);
    }

    @Override
    public String cadastrar(AbstractEntidade entidade) {
        CrudRepository dao = daos.get(entidade.getClass().getName());
        dao.save(entidade);
        return null;
    }

    @Override
    public String excluir(AbstractEntidade entidade) {
        String nmClasse = entidade.getClass().getName();
        CrudRepository dao = daos.get(nmClasse);
        dao.delete(entidade);
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

    public void alterarCupom(AbstractEntidade entidade){
        CrudRepository dao = daos.get(entidade.getClass().getName());
        dao.save(entidade);
    }

    public void utilizarCupons(Compra compra){
        for(int i = 0; i < compra.getCupons().size(); i++){
            Cupom cupom = cupomDAO.findById(compra.getCupons().get(i)).orElse(null);
            cupom.setCompra(compra);
            cupom.setCliente(null);
            alterarCupom(cupom);
        }
    }

    public void cadastrarDependencias(Compra compra){
        addItensCompra(compra);
        if(compra.getCupons().size() > 0){
            utilizarCupons(compra);
        }
        vincularCartaoCompra(compra);
    }

    public void cadastrarCartaoCompra(AbstractEntidade entidade){
        CrudRepository dao = daos.get(entidade.getClass().getName());
        dao.save(entidade);
    }

    public void vincularCartaoCompra(Compra compra){
        for(int i = 0; i < compra.getCartoes().size(); i++){
            CompraCartao compraCartao = new CompraCartao();
            Cartao cartao = cartaoDAO.findById(compra.getCartoes().get(i)).orElse(null);
            compraCartao.setCartao(cartao);
            compraCartao.setCompra(compra);
            cadastrarCartaoCompra(compraCartao);
        }
    }

    public void addItensCompra(Compra compra){
        for(int i = 0; i < compra.getCliente().getItensVinculados().size(); i++){
            CompraLivro compraLivro = compra.getCliente().getItensVinculados().get(i);
            compraLivro.setCompra(compra);
            compraLivro.setCliente(null);
            efetivarCompraLivro(compraLivro);
        }
    }

    public void efetivarCompraLivro(AbstractEntidade entidade){
        CrudRepository dao = daos.get(entidade.getClass().getName());
        dao.save(entidade);
    }

    public List<Compra> listarTodas() {
        List<Compra> compras = new ArrayList<>();
        compraDAO.findAll().forEach(compras::add);
        return compras;
    }

    public Compra getCompra(Integer id){
        Compra compra = compraDAO.findById(id).orElse(null);
        return compra;
    }

    public CompraLivro getCompraLivro(Integer id){
        CompraLivro compraLivro = compraLivroDAO.findById(id).orElse(null);
        return compraLivro;
    }

    public void gerarTroca(Compra compraForm, Integer id){
        Compra compra = getCompra(id);
        if(compra.getItensVinculados().size() == compraForm.getCompraLivros().size()){
            trocarPedido(compra);
        }else{
            trocarItens(compraForm.getCompraLivros(), compra);
        }
    }

    public void trocarPedido(Compra compra){
        compra.setStatus(StatusEnum.TROCA);
        cadastrar(compra);
    }

    public void trocarItens(List<Integer> itens, Compra compra){
        for(int i = 0; i < itens.size(); i++){
            Compra novaCompra = new Compra();
            novaCompra.setCliente(compra.getCliente());
            novaCompra.setStatus(StatusEnum.TROCA);
            novaCompra.setEnderecoEntrega(compra.getEnderecoEntrega());
            CompraLivro compraLivro =  compraLivroDAO.findById(itens.get(i)).orElse(null);
            novaCompra.setValorTotal(compraLivro.getLivro().getPreco());
            cadastrar(novaCompra);
            gerarNovosPedidos(compraLivro, novaCompra);
        }
    }

    public void gerarNovosPedidos(CompraLivro compraLivro, Compra compra){
        CompraLivro novaCompraLivro = new CompraLivro();
        novaCompraLivro.setCompra(compra);
        novaCompraLivro.setLivro(compraLivro.getLivro());
        novaCompraLivro.setQuantidade(compraLivro.getQuantidade());
        efetivarCompraLivro(novaCompraLivro);

    }

    public void cadastrarCupomTroca(Compra compra){
        Cupom cupom = new Cupom();
        cupom.setCliente(compra.getCliente());
        cupom.setValor(compra.getValorTotal());
        cupom.setNome("Troca" + Integer.toString(compra.getId()));
        cupom.setTipoCupom(TipoCupomEnum.TROCA);
        cadastrarCupom(cupom);
    }

    public void cadastrarCupom(AbstractEntidade entidade){
        CrudRepository dao = daos.get(entidade.getClass().getName());
        dao.save(entidade);
    }
}
