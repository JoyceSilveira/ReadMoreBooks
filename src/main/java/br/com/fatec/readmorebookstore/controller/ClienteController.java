package br.com.fatec.readmorebookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @GetMapping("/add-cliente")
    public String mostraFormularioCadastro() {
        return "cadastro-cliente";
    }

    @GetMapping("/salvar-cliente")
    public String salvar() {
        return "perfil-cliente";
    }

    @GetMapping("/update-senha")
    public String upSenha() {
        return "update-cliente-senha";
    }

    @GetMapping("/update-endereco")
    public String upEndereco() { return "update-cliente-end"; }

    @GetMapping("/update-dados")
    public String upDadosPessoais() {
        return "update-cliente-dados";
    }

    @GetMapping("/add-endereco-cobranca")
    public String addEndCobranca() {
        return "perfil-cliente";
    }

    @GetMapping("/add-endereco-entrega")
    public String addEndEntrega() {
        return "perfil-cliente";
    }

}
