package br.com.fatec.readmorebookstore.controller;

import br.com.fatec.readmorebookstore.dominio.Cliente;
import br.com.fatec.readmorebookstore.dto.ClienteDTO;
import br.com.fatec.readmorebookstore.facade.ClienteFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    public String salvarCliente(Cliente cliente, Errors errors) {
        String msgRetorno = clienteFacade.cadastrar(cliente);
        if (msgRetorno != null && !msgRetorno.isEmpty()) {
            errors.rejectValue("saveErrors", msgRetorno);
            return "redirect:/clientes/?errors="+errors.getAllErrors();
        }

        return "perfil-cliente";
    }

    @GetMapping("/")
    public String erroSalvarCliente(Model model, @RequestParam("errors") List<ObjectError> errors) {
        model.addAttribute("errors", errors);
        return null;
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
