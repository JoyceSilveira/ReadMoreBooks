package br.com.fatec.readmorebookstore.controller;

import br.com.fatec.readmorebookstore.dominio.*;
import br.com.fatec.readmorebookstore.facade.ClienteFacade;
import br.com.fatec.readmorebookstore.facade.LivroFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Log4j2
@Controller
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroFacade livroFacade;
    @Autowired
    private ClienteFacade clienteFacade;

    @GetMapping("/editar-livro")
    public String EditLivro(){ return "update-livro"; }

    @GetMapping("/lista-livro")
    public String listaLivro(){
        return "lista-livro";
    }

    @GetMapping("/add-livro")
    public String mostraFormularioLivro(){ return "cadastro-livro"; }

    @GetMapping("/principal/{clienteId}")
    public String principal(@PathVariable("clienteId") Integer clienteId, Model model){
        model.addAttribute("livros", livroFacade.listarTodos());
        model.addAttribute("cliente", clienteFacade.getCliente(clienteId));
        return "busca-livro";
    }

    @GetMapping("/detalhes-livro/{id}/{clienteId}")
    public String detalhesLivro(@PathVariable("id") Integer id, Model model, CompraLivro compraLivro, @PathVariable("clienteId") Integer clienteId){
        model.addAttribute("livro", livroFacade.getLivro(id));
        model.addAttribute("cliente", clienteFacade.getCliente(clienteId));
        return "detalhes-livro";
    }

    @GetMapping("/detalhes-livro-admin")
    public String detalhesLivroAdmin(){ return "detalhes-livro-admin"; }

    @GetMapping("/justificativa-inativacao")
    public String justificativaInativacao(){ return "justificativa-inativacao-livro"; }

    @GetMapping("/estoque-troca/{decisao}")
    public String controlarEstoqueTroca(){

        return "justificativa-inativacao-livro";
    }
}
