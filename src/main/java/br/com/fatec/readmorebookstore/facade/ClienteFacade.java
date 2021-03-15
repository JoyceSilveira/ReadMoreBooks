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
public class ClienteFacade implements IFacade {


    private final ClienteDAO clienteDAO;
    private final EnderecoDAO enderecoDAO;
    private final CartaoDAO cartaoDAO;
    private final CidadeDAO cidadeDAO;
    private final ComplementarDtCadastro cDataCadastro;
    private final ValidadorCpf vCpf;
    private final ValidadorDadosObrigatoriosCliente vCliente;
    private final ValidadorExistenciaClienteCpf vExistenciaClienteCpf;
    private final ValidadorSenhaCliente vSenhaCliente;
    private Map<String, CrudRepository> daos;
    private Map<String, List<IStrategy>> rns;

    @Autowired
    public ClienteFacade(
            ClienteDAO clienteDAO, EnderecoDAO enderecoDAO,
            CartaoDAO cartaoDAO, CidadeDAO cidadeDAO,
            ComplementarDtCadastro cDataCadastro, ValidadorCpf vCpf,
            ValidadorDadosObrigatoriosCliente vCliente, ValidadorExistenciaClienteCpf vExistenciaClienteCpf,
            ValidadorSenhaCliente vSenhaCliente) {
        this.clienteDAO = clienteDAO;
        this.enderecoDAO = enderecoDAO;
        this.cartaoDAO = cartaoDAO;
        this.cidadeDAO = cidadeDAO;
        this.cDataCadastro = cDataCadastro;
        this.vCpf = vCpf;
        this.vCliente = vCliente;
        this.vExistenciaClienteCpf = vExistenciaClienteCpf;
        this.vSenhaCliente = vSenhaCliente;
        definirDAOS(clienteDAO, enderecoDAO, cartaoDAO, cidadeDAO);
        definirRNS(cDataCadastro, vCpf, vCliente, vExistenciaClienteCpf, vSenhaCliente);
    }

    private void definirRNS(
        ComplementarDtCadastro cDataCadastro, ValidadorCpf vCpf,
        ValidadorDadosObrigatoriosCliente vCliente, ValidadorExistenciaClienteCpf vExistenciaClienteCpf,
        ValidadorSenhaCliente vSenhaCliente
    ) {
        rns = new HashMap<>();

        List<IStrategy> rnsCliente = new ArrayList<>();
        rnsCliente.add(cDataCadastro);
        rnsCliente.add(vCpf);
        rnsCliente.add(vCliente);
        rnsCliente.add(vExistenciaClienteCpf);
        rnsCliente.add(vSenhaCliente);

        rns.put(Cliente.class.getName(), rnsCliente);
    }

    private void definirDAOS(
        ClienteDAO clienteDAO, EnderecoDAO enderecoDAO,
        CartaoDAO cartaoDAO, CidadeDAO cidadeDAO
    ) {
        daos = new HashMap<>();
        daos.put(Cliente.class.getName(), clienteDAO);
        daos.put(Endereco.class.getName(), enderecoDAO);
        daos.put(Cartao.class.getName(), cartaoDAO);
        daos.put(Cidade.class.getName(), cidadeDAO);
    }

    @Override
    public String cadastrar(AbstractEntidade entidade) {
        Cliente cliente = (Cliente) entidade;
        List<AbstractEntidade> entidades = new ArrayList<>();
        List<Endereco> enderecos = getListaEnderecos(cliente);
        cliente.setEnderecos(enderecos);
        entidades.add(cliente);
        entidades.add(getCartao(cliente));
        entidades.add(getEnderecoCobranca(cliente));
        entidades.add(getEnderecoEntrega(cliente));
        StringBuilder msgRetorno = new StringBuilder();
        for (AbstractEntidade entidadeSalvar : entidades) {
            String nmClasse = entidadeSalvar.getClass().getName();
            String msg = executarRegras(entidadeSalvar);
            if (msg == null) {
                CrudRepository dao = daos.get(nmClasse);
                dao.save(entidadeSalvar);
            } else {
                msgRetorno.append(msg);
            }
        }
        if (msgRetorno.length() > 0) {
            return msgRetorno.toString();
        }
        return null;
    }

    private List<Endereco> getListaEnderecos(Cliente cliente) {
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(cliente.getEnderecoCobranca());
        enderecos.add(cliente.getEnderecoEntrega());
        return enderecos;
    }

    private Cartao getCartao(Cliente cliente) {
        Cartao cartao = cliente.getCartao();
        cartao.setCliente(cliente);
        return cartao;
    }

    private Endereco getEnderecoCobranca(Cliente cliente) {
        Endereco endereco = cliente.getEnderecoCobranca();
        endereco.setCliente(cliente);
        return endereco;
    }

    private Endereco getEnderecoEntrega(Cliente cliente) {
        Endereco endereco = cliente.getEnderecoEntrega();
        endereco.setCliente(cliente);
        return endereco;
    }

    private String executarRegras(AbstractEntidade entidade) {
        String nmClasse = entidade.getClass().getName();
        StringBuilder msg = new StringBuilder();

        List<IStrategy> regras = rns.get(nmClasse);

        if (regras != null) {
            for (IStrategy s : regras) {
                String m = s.processar(entidade);

                if (m != null) {
                    msg.append(m);
                    msg.append("\n");
                }
            }
        }

        if (msg.length() > 0)
            return msg.toString();
        else
            return null;
    }

    @Override
    public String excluir(AbstractEntidade entidade) {
        if (entidade instanceof Cliente) {
            Cliente cliente = (Cliente) entidade;
            if(cliente.isAtivo()){
                cliente.setAtivo(false);
            }else {
                cliente.setAtivo(true);
            }
            String nmClasse = entidade.getClass().getName();
            CrudRepository dao = daos.get(nmClasse);
            dao.save(cliente);
            return "Cliente inativado com sucesso!";
        }
        return null;
    }

    @Override
    public String alterar(AbstractEntidade entidade) {
        return cadastrar(entidade);
    }

    @Override
    public List<AbstractEntidade> consultar(AbstractEntidade entidade) {
        return null;
    }

    public Cliente getCliente(Integer id){
        Cliente cliente = clienteDAO.findById(id).orElse(null);
        return cliente;
    }

    public Cartao pegarCartao(Integer id){
        Cartao cartao = cartaoDAO.findById(id).orElse(null);
        return cartao;
    }

    public Endereco pegarEndereco(Integer id){
        Endereco endereco = enderecoDAO.findById(id).orElse(null);
        return endereco;
    }

    public void alterarDados(AbstractEntidade entidade) {
        CrudRepository dao = daos.get(entidade.getClass().getName());
        dao.save(entidade);
    }

    public String alterarSenha(AbstractEntidade entidade, String senha) {
        Cliente cliente = (Cliente) entidade;
        cliente.setSenha(senha);
        String nmClasse = entidade.getClass().getName();
        CrudRepository dao = daos.get(nmClasse);
        dao.save(cliente);
        return "Senha alterada com sucesso!";
    }

    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        clienteDAO.findAll().forEach(clientes::add);
        return clientes;
    }
}
