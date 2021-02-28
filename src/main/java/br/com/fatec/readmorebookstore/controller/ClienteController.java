package br.com.fatec.readmorebookstore.controller;

import br.com.fatec.readmorebookstore.dto.ClienteDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @GetMapping("/add-cliente")
    public String mostraFormularioCadastro(ClienteDTO clienteDTO) {
        return "cadastro-cliente";
    }

    @PostMapping("/salvar-cliente")
    public String salvarCliente(ClienteDTO clienteDTO) {
        return "perfil-cliente";
    }

    @GetMapping("/list-cliente")
    public String mostraListaClientes(Model model) {
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
