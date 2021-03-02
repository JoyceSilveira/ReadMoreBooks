package br.com.fatec.readmorebookstore.facade;

import br.com.fatec.readmorebookstore.dao.*;
import br.com.fatec.readmorebookstore.dominio.*;
import br.com.fatec.readmorebookstore.negocio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClienteFacade implements IFachada {


    private final ClienteDAO clienteDAO;
    private final EnderecoDAO enderecoDAO;
    private final CartaoDAO cartaoDAO;
    private final CidadeDAO cidadeDAO;
    private final ComplementarDtCadastro cDataCadastro;
    private final ComplementarCidade cCidade;
    private final ValidadorCpf vCpf;
    private final ValidadorDadosObrigatoriosCliente vCliente;
    private final ValidadorExistenciaClienteCpf vExistenciaClienteCpf;
    private Map<String, CrudRepository> daos;
    private Map<String, List<IStrategy>> rns;

    @Autowired
    public ClienteFacade(
            ClienteDAO clienteDAO, EnderecoDAO enderecoDAO,
            CartaoDAO cartaoDAO, CidadeDAO cidadeDAO,
            ComplementarDtCadastro cDataCadastro, ComplementarCidade cCidade, ValidadorCpf vCpf,
            ValidadorDadosObrigatoriosCliente vCliente, ValidadorExistenciaClienteCpf vExistenciaClienteCpf) {
        this.clienteDAO = clienteDAO;
        this.enderecoDAO = enderecoDAO;
        this.cartaoDAO = cartaoDAO;
        this.cidadeDAO = cidadeDAO;
        this.cDataCadastro = cDataCadastro;
        this.cCidade = cCidade;
        this.vCpf = vCpf;
        this.vCliente = vCliente;
        this.vExistenciaClienteCpf = vExistenciaClienteCpf;
        definirDAOS(clienteDAO, enderecoDAO, cartaoDAO, cidadeDAO);
        definirRNS(cDataCadastro, cCidade, vCpf, vCliente, vExistenciaClienteCpf);
    }

    private void definirRNS(
        ComplementarDtCadastro cDataCadastro, ComplementarCidade cCidade, ValidadorCpf vCpf,
        ValidadorDadosObrigatoriosCliente vCliente, ValidadorExistenciaClienteCpf vExistenciaClienteCpf
    ) {
        rns = new HashMap<>();

        List<IStrategy> rnsAluno = new ArrayList<>();
        rnsAluno.add(cDataCadastro);
        rnsAluno.add(vCpf);
        rnsAluno.add(vCliente);
        rnsAluno.add(vExistenciaClienteCpf);

        List<IStrategy> rnsEndereco = new ArrayList<>();
        rnsEndereco.add(cCidade);

        rns.put(Cliente.class.getName(), rnsAluno);
        rns.put(Endereco.class.getName(), rnsEndereco);
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
        String nmClasse = entidade.getClass().getName();
        CrudRepository dao = daos.get(nmClasse);
        dao.delete(entidade);
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

    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        clienteDAO.findAll().forEach(clientes::add);
        return clientes;
    }
}
