package br.com.fatec.readmorebookstore.controller;

import br.com.fatec.readmorebookstore.dominio.Cliente;
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

    @GetMapping("/update-senha/{id}")
    public String upSenha() {
        return "update-cliente-senha";
    }

    @GetMapping("/update-endereco-entrega/{id}")
    public String upEndEntrega() { return "update-cliente-end-entrega"; }

    @GetMapping("/update-endereco-cobranca/{id}")
    public String upEndCobranca() { return "update-cliente-end-cobranca"; }

    @GetMapping("/update-dados/{id}")
    public String upDadosPessoais() {
        return "update-cliente-dados";
    }

    @GetMapping("/update-cartao/{id}")
    public String upCartao() {
        return "update-cartao";
    }

    @GetMapping("/add-endereco-cobranca/{id}")
    public String addEndCobranca() {
        return "add-cliente-end-cobranca";
    }

    @GetMapping("/add-endereco-entrega/{id}")
    public String addEndEntrega() {
        return "add-cliente-end-entrega";
    }

    @GetMapping("/add-cartao/{id}")
    public String addCartao() { return "add-cartao"; }

}
