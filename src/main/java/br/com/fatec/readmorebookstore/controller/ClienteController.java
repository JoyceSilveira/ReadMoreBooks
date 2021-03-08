package br.com.fatec.readmorebookstore.controller;

import br.com.fatec.readmorebookstore.dominio.*;
import br.com.fatec.readmorebookstore.facade.ClienteFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

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
        Cliente cliente = clienteFacade.mostrarPerfil(id);
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
        Cliente cliente = clienteFacade.mostrarPerfil(id);
        model.addAttribute("cliente", cliente);
        return "update-cliente-senha";
    }

    @PostMapping("/atualizar-senha/{id}")
    public String atualizarSenha(Cliente cliente, @PathVariable("id") Integer id) {
        try {
            cliente.setId(id);
            clienteFacade.alterar(cliente);
            return "redirect:/clientes/perfil-cliente/" + cliente.getId() + "";
        } catch (Exception e) {
            log.error("Falha ao salvar cliente.", e);
            return "Falha ao salvar cliente.";
        }
    }

    @GetMapping("/editar-endereco/{id}")
    public String EditEndereco(@PathVariable("id") Integer id, Model model) {
        Endereco endereco = clienteFacade.pegarEndereco(id);
        model.addAttribute("endereco", endereco);
        return "update-cliente-endereco";
    }

    @PostMapping("/atualizar-endereco/{id}")
    public String atualizarEndereco(Endereco endereco, @PathVariable("id") Integer id) {
        try {
            endereco.setId(id);
            clienteFacade.alterar(endereco);
            return "redirect:/clientes/perfil-cliente/" + endereco.getId() + "";
        } catch (Exception e) {
            log.error("Falha ao salvar cliente.", e);
            return "Falha ao salvar cliente.";
        }
    }

    @GetMapping("/editar-dados/{id}")
    public String EditDadosPessoais(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteFacade.mostrarPerfil(id);
        model.addAttribute("cliente", cliente);
        return "update-cliente-dados";
    }

    @PostMapping("/atualizar-dados/{id}")
    public String atualizarDados(Cliente cliente, @PathVariable("id") Integer id) {
        try {
            cliente.setId(id);
            clienteFacade.alterar(cliente);
            return "redirect:/clientes/perfil-cliente/" + cliente.getId() + "";
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

    @PostMapping("/atualizar-cartao/{id}")
    public String atualizarCartao(Cartao cartao, @PathVariable("id") Integer id) {
        try {
            cartao.setId(id);
            clienteFacade.alterarCartao(cartao);
            return "redirect:/clientes/perfil-cliente/" + cartao.getId() + "";
        } catch (Exception e) {
            log.error("Falha ao salvar cliente.", e);
            return "Falha ao salvar cliente.";
        }
    }

    @GetMapping("/add-endereco/{id}")
    public String addEndCobranca() {
        return "add-cliente-end-cobranca";
    }

    @GetMapping("/add-cartao/{id}")
    public String addCartao() { return "add-cartao"; }

}
