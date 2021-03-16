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
@RequestMapping("/livros")
public class LivroController {

    @GetMapping("/editar-livro")
    public String EditLivro(){ return "update-livro"; }

    @GetMapping("/lista-livro")
    public String listaLivro(){ return "lista-livro"; }

    @GetMapping("/detalhes-livro")
    public String detalhesLivro(){ return "detalhes-livro"; }

    @GetMapping("/add-livro")
    public String mostraFormularioLivro(){ return "cadastro-livro"; }

    @GetMapping("/principal")
    public String principal(){ return "busca-livro"; }

    @GetMapping("/detalhes-livro-admin")
    public String detalhesLivroAdmin(){ return "detalhes-livro-admin"; }
}
