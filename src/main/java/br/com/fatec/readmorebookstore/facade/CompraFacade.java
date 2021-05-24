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
    private final ValidadorValorCartao vValorCartao;
    private final ValidadorValorCupons vValorCupons;
    private final ValidadorItemExistenteCarrinho vItemExistenteCarrinho;
    private final ValidadorQuantidadeLivro vQuantidadeLivro;
    private final ValidadorCartaoCompra vCartaoCompra;
    private Map<String, CrudRepository> daos;
    private Map<String, List<IStrategy>> rns;
    @Autowired
    private LivroFacade livroFacade;
    @Autowired
    private ClienteFacade clienteFacade;

    @Autowired
    public CompraFacade(
            CompraDAO compraDAO, CompraLivroDAO compraLivroDAO, CupomDAO cupomDAO, CartaoDAO cartaoDAO, CompraCartaoDAO compraCartaoDAO, ValidadorValorCartao vValorCartao, ValidadorValorCupons vValorCupons,
            ValidadorItemExistenteCarrinho vItemExistenteCarrinho, ValidadorQuantidadeLivro vQuantidadeLivro, ValidadorCartaoCompra vCartaoCompra
    ){
        this.compraDAO = compraDAO;
        this.compraLivroDAO = compraLivroDAO;
        this.cupomDAO = cupomDAO;
        this.cartaoDAO = cartaoDAO;
        this.compraCartaoDAO = compraCartaoDAO;
        this.vValorCartao = vValorCartao;
        this.vValorCupons = vValorCupons;
        this.vItemExistenteCarrinho = vItemExistenteCarrinho;
        this.vQuantidadeLivro = vQuantidadeLivro;
        this.vCartaoCompra = vCartaoCompra;
        definirDAOS(compraDAO, compraLivroDAO, cupomDAO, cartaoDAO, compraCartaoDAO);
        definirRNSCompra(vValorCartao, vValorCupons, vItemExistenteCarrinho, vQuantidadeLivro);
    }

    private void definirRNSCompra(
            ValidadorValorCartao vValorCartao, ValidadorValorCupons vValorCupons,
            ValidadorItemExistenteCarrinho vItemExistenteCarrinho, ValidadorQuantidadeLivro vQuantidadeLivro
    ) {
        rns = new HashMap<>();

        List<IStrategy> rnsCompra = new ArrayList<>();
        rnsCompra.add(vValorCupons);
        rnsCompra.add(vValorCartao);

        rns.put(Compra.class.getName(), rnsCompra);

        List<IStrategy> rnsCompraLivro = new ArrayList<>();
        rnsCompraLivro.add(vItemExistenteCarrinho);
        rnsCompraLivro.add(vQuantidadeLivro);

        rns.put(CompraLivro.class.getName(), rnsCompraLivro);
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
        String msg = executarRegras(entidade);
        if(msg == null){
            CrudRepository dao = daos.get(entidade.getClass().getName());
            dao.save(entidade);
            return null;
        }
        return msg;
    }

    private String executarRegras(AbstractEntidade entidade) {
        String nmClasse = entidade.getClass().getName();

        List<IStrategy> regras = rns.get(nmClasse);

        for (IStrategy s : regras) {
            String m = s.processar(entidade);
            if (m != null) {
                return m;
            }
        }
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

    public String cadastrarItem(AbstractEntidade entidade){
        CompraLivro compraLivro = (CompraLivro) entidade;
        String msg = executarRegras(entidade);
        if(msg == null){
            livroFacade.retirarEstoque(compraLivro.getQuantidade(), compraLivro.getLivro());
            CrudRepository dao = daos.get(entidade.getClass().getName());
            dao.save(entidade);
            return null;
        }
        return msg;
    }

    public void alterarCupom(AbstractEntidade entidade){
        CrudRepository dao = daos.get(entidade.getClass().getName());
        dao.save(entidade);
    }

    public void utilizarCupons(Compra compra){
        Double desconto = 0.0;
        for(int i = 0; i < compra.getCupons().size(); i++){
            Cupom cupom = cupomDAO.findById(compra.getCupons().get(i)).orElse(null);
            desconto += cupom.getValor();
            cupom.setCompra(compra);
            cupom.setCliente(null);
            alterarCupom(cupom);
        }
        if(desconto > compra.getValorTotal()){
            cadastrarCupomDiferenca(desconto - compra.getValorTotal(), compra);
        }
    }

    public void cadastrarCupomDiferenca(Double valor, Compra compra){
        Cupom cupom = new Cupom();
        cupom.setTipoCupom(TipoCupomEnum.TROCA);
        cupom.setValor(valor);
        cupom.setNome("Diferenca" + Integer.toString(compra.getId()));
        cupom.setCliente(compra.getCliente());
        cadastrarCupom(cupom);
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
        Integer msg = 0;
        for(int i = 0; i < compra.getCartoes().size(); i++){
            CompraCartao compraCartao = new CompraCartao();
            Cartao cartao = cartaoDAO.findById(compra.getCartoes().get(i)).orElse(null);
            compraCartao.setCartao(cartao);
            compraCartao.setCompra(compra);
            cadastrarCartaoCompra(compraCartao);
            msg += verificarCartao(cartao);
        }
        Compra compraProcessada = getCompra(compra.getId());
        if(msg > 0){
            compraProcessada.setStatus(StatusEnum.REPROVADA);
            alterarDados(compraProcessada);
        } else {
            compraProcessada.setStatus(StatusEnum.APROVADA);
            alterarDados(compraProcessada);
        }
    }

    public Integer verificarCartao(Cartao cartao){
        String msg = vCartaoCompra.processar(cartao);
        if(msg == null){
            return 0;
        } else {
            return 1;
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

    public void cadastrarCupomPromocional(Cupom cupom){
        List<Cliente> clientes = clienteFacade.listarTodos();
        for(Cliente cliente : clientes){
            cupom.setCliente(cliente);
            cadastrarCupom(cupom);
        }
    }

    public String alterarQuantidade(CompraLivro compraLivro, CompraLivro compraLivroForm){
        if(compraLivroForm.getQuantidade() <= compraLivro.getLivro().getEstoque()){
            livroFacade.retornarEstoqueCarrinho(compraLivro);
            livroFacade.retirarEstoque(compraLivroForm.getQuantidade(), compraLivro.getLivro());
            compraLivro.setQuantidade(compraLivroForm.getQuantidade());
            alterarDados(compraLivro);
            return null;
        }
        return "Quantidade maior do que estoque";
    }

    public void alterarDados(AbstractEntidade entidade){
        CrudRepository dao = daos.get(entidade.getClass().getName());
        dao.save(entidade);
    }

    public HashMap<String, Integer> contarLivrosVendidos(){
        Map<String, Integer> vendaLivros = new HashMap<>();
        List<Livro> livros = livroFacade.listarTodos();
        List<Compra> compras = listarTodas();
        for(int i=0; i < livros.size(); i++){
            Integer quantidade = 0;
            for(int j=0; j < compras.size(); j++){
                for(int c=0; c < compras.get(j).getItensVinculados().size(); c++){
                    if(compras.get(j).getItensVinculados().get(c).getLivro() == livros.get(i)){
                        quantidade += compras.get(j).getItensVinculados().get(c).getQuantidade();
                    }
                }
            }
            vendaLivros.put(livros.get(i).getTitulo(), quantidade);
        }
        return (HashMap<String, Integer>) vendaLivros;
    }

    public HashMap<String, Integer> contarCategoriasVendidas(){
        Map<String, Integer> vendaCategorias = new HashMap<>();
        List<Categoria> categorias = livroFacade.pegarCategorias();
        List<Compra> compras = listarTodas();
        for(int i=0; i < categorias.size(); i++){
            Integer quantidade = 0;
            for(int j=0; j < compras.size(); j++){
                for(int c=0; c < compras.get(j).getItensVinculados().size(); c++){
                    for(int z=0; z < compras.get(j).getItensVinculados().get(c).getLivro().getCategoriasVinculadas().size(); z++){
                        if(compras.get(j).getItensVinculados().get(c).getLivro().getCategoriasVinculadas().get(z).getCategoria() == categorias.get(i)){
                            quantidade += compras.get(j).getItensVinculados().get(c).getQuantidade();
                        }
                    }
                }
            }
            vendaCategorias.put(categorias.get(i).getNome(), quantidade);
        }
        return (HashMap<String, Integer>) vendaCategorias;
    }
}
