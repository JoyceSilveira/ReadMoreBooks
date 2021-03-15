package br.com.fatec.readmorebookstore.controller;

import br.com.fatec.readmorebookstore.dominio.*;
import br.com.fatec.readmorebookstore.facade.ClienteFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Log4j2
@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteFacade clienteFacade;

    @GetMapping("/add-cliente")
    public String mostraFormularioCadastro(Cliente cliente) {
        return "cadastro-cliente";
    }

    @PostMapping("/salvar-cliente")
    public String salvarCliente(Cliente cliente) {
        try{
            clienteFacade.cadastrar(cliente);
            return "redirect:/clientes/perfil-cliente/" + cliente.getId() + "";
        } catch (Exception e) {
            log.error("Falha ao salvar cliente.", e);
            return "Falha ao salvar cliente";
        }
    }

    @GetMapping("/perfil-cliente/{id}")
    public String mostraPerfilCliente(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteFacade.getCliente(id);
        model.addAttribute("cliente", cliente);
        return "perfil-cliente";
    }

    @GetMapping("/list-cliente")
    public String mostraListaClientes(Model model) {
        model.addAttribute("clientes", clienteFacade.listarTodos());
        return "lista-cliente";
    }

    @GetMapping("/editar-senha/{id}")
    public String EditSenha(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteFacade.getCliente(id);
        model.addAttribute("cliente", cliente);
        return "update-cliente-senha";
    }

    @PostMapping("/atualizar-senha/{id}")
    public String atualizarSenha(Cliente clienteForm, @PathVariable("id") Integer id) {
        try {
            String senha = clienteForm.getSenha();
            Cliente cliente = clienteFacade.getCliente(id);
            clienteFacade.alterarSenha(cliente, senha);
            return "redirect:/clientes/perfil-cliente/" + cliente.getId() + "";
        } catch (Exception e) {
            log.error("Falha ao alterar senha.", e);
            return "Falha ao alterar senha.";
        }
    }

    @GetMapping("/editar-endereco/{id}")
    public String EditEndereco(@PathVariable("id") Integer id, Model model) {
        Endereco endereco = clienteFacade.pegarEndereco(id);
        model.addAttribute("endereco", endereco);
        return "update-cliente-endereco";
    }

    @PostMapping("/atualizar-endereco/{id}/{clienteId}")
    public String atualizarEndereco(Endereco endereco, @PathVariable("id") Integer id, @PathVariable("clienteId") Integer clienteId) {
        try {
            Cliente cliente = clienteFacade.getCliente(clienteId);
            endereco.setId(id);
            endereco.setCliente(cliente);
            clienteFacade.alterarDados(endereco);
            return "redirect:/clientes/perfil-cliente/" + endereco.getId() + "";
        } catch (Exception e) {
            log.error("Falha ao atualizar endereco.", e);
            return "Falha ao atualizar endereco.";
        }
    }

    @GetMapping("/editar-dados/{id}")
    public String EditDadosPessoais(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteFacade.getCliente(id);
        model.addAttribute("cliente", cliente);
        return "update-cliente-dados";
    }

    @PostMapping("/atualizar-dados/{id}")
    public String atualizarDados(Cliente clienteForm, @PathVariable("id") Integer id) {
        try {
            Cliente cliente = clienteFacade.getCliente(id);
            clienteForm.setId(id);
            clienteForm.setDataCadastro(cliente.getDataCadastro());
            clienteForm.setSenha(cliente.getSenha());
            clienteFacade.alterarDados(clienteForm);
            return "redirect:/clientes/perfil-cliente/" + clienteForm.getId() + "";
        } catch (Exception e) {
            log.error("Falha ao salvar cliente.", e);
            return "Falha ao salvar cliente.";
        }
    }

    @GetMapping("/editar-cartao/{id}")
    public String EditCartao(@PathVariable("id") Integer id, Model model) {
        Cartao cartao = clienteFacade.pegarCartao(id);
        model.addAttribute("cartao", cartao);
        return "update-cartao";
    }

    @PostMapping("/atualizar-cartao/{id}/{clienteId}")
    public String atualizarCartao(Cartao cartao, @PathVariable("id") Integer id, @PathVariable("clienteId") Integer clienteId) {
        try {
            Cliente cliente = clienteFacade.getCliente(clienteId);
            cartao.setId(id);
            cartao.setCliente(cliente);
            clienteFacade.alterarDados(cartao);
            return "redirect:/clientes/perfil-cliente/" + cartao.getCliente().getId() + "";
        } catch (Exception e) {
            log.error("Falha ao atualizar cartão.", e);
            return "Falha ao atualizar cartão.";
        }
    }

    @GetMapping("/inativar-ativar/{id}")
    public String inativarCliente(@PathVariable("id") Integer id){
        try {
            Cliente cliente = clienteFacade.getCliente(id);
            clienteFacade.excluir(cliente);
            return "redirect:/clientes/list-cliente";
        } catch (Exception e) {
            log.error("Falha ao inativar.", e);
            return "Falha ao inativar cliente.";
        }
    }

    @GetMapping("/add-endereco/{id}")
    public String addEndCobranca(Endereco endereco) {
        return "add-cliente-endereco";
    }

    @GetMapping("/add-cartao/{id}")
    public String addCartao(Cartao cartao) { return "add-cartao"; }

    @GetMapping("/principal")
    public String principal(){ return "busca-livro"; }

    @GetMapping("/add-livro")
    public String mostraFormularioLivro(){ return "cadastro-livro"; }

    @GetMapping("/carrinho-compras")
    public String carrinhoCompras(){ return "carrinho"; }

    @GetMapping("/detalhes-livro")
    public String detalhesLivro(){ return "detalhes-livro"; }

    @GetMapping("/detalhes-livro-admin")
    public String detalhesLivroAdmin(){ return "detalhes-livro-admin"; }

    @GetMapping("/lista-compras")
    public String listaCompras(){ return "list-compras-cliente"; }

    @GetMapping("/lista-compras-admin")
    public String listaComprasAdmin(){ return "list-compras"; }

    @GetMapping("/lista-livro")
    public String listaLivro(){ return "lista-livro"; }

    @GetMapping("/pedido")
    public String pedido(){ return "pedido"; }

    @GetMapping("/editar-livro")
    public String EditLivro(){ return "update-livro"; }

    @GetMapping("/menu-admin")
    public String menuAdmin(){ return "menu-admin"; }
}
