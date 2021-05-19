package br.com.fatec.readmorebookstore.controller;

import br.com.fatec.readmorebookstore.dominio.*;
import br.com.fatec.readmorebookstore.facade.ClienteFacade;
import br.com.fatec.readmorebookstore.facade.CompraFacade;
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
    @Autowired
    private CompraFacade compraFacade;

    @GetMapping("/editar-livro/{id}")
    public String EditLivro(Model model, @PathVariable("id") Integer id){
        Livro livro = livroFacade.getLivro(id);
        model.addAttribute("livro", livro);
        model.addAttribute("categorias", livroFacade.pegarCategorias());
        return "update-livro";
    }

    @GetMapping("/atualizar-livro/{id}")
    public String AtualizarLivro(Livro livroForm, @PathVariable("id") Integer id){
        Livro livro = livroFacade.getLivro(id);
        livroForm.setEstoque(livro.getEstoque());
        livroFacade.AtualizarDados(livro, livroForm);
        return "redirect:/livros/detalhes-livro-admin/" + id + "";
    }

    @GetMapping("/lista-livro")
    public String listaLivro(Model model){
        model.addAttribute("livros", livroFacade.listarTodos());
        return "lista-livro";
    }

    @GetMapping("/add-livro")
    public String mostraFormularioLivro(Livro livro, Model model){
        model.addAttribute("categorias", livroFacade.pegarCategorias());
        return "cadastro-livro";
    }

    @GetMapping("/cadastrar-livro")
    public String cadastrarLivro(Livro livro){
        livroFacade.cadastrar(livro);
        return "redirect:/livros/lista-livro";
    }

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

    @GetMapping("/detalhes-livro-admin/{id}")
    public String detalhesLivroAdmin(@PathVariable("id") Integer id, Model model){
        Livro livro = livroFacade.getLivro(id);
        model.addAttribute("livro", livro);
        return "detalhes-livro-admin";
    }

    @GetMapping("/justificativa-inativacao/{id}")
    public String justificativaInativacao(@PathVariable("id") Integer id, LogDesativacaoLivro logDesativacaoLivro, Model model){
        Livro livro = livroFacade.getLivro(id);
        model.addAttribute("livro", livro);
        return "justificativa-inativacao-livro";
    }

    @GetMapping("/inativar-ativar-livro/{id}")
    public String InativarAtivarLivro(@PathVariable("id") Integer id, LogDesativacaoLivro logDesativacaoLivro){
        Livro livro = livroFacade.getLivro(id);
        logDesativacaoLivro.setLivro(livro);
        livroFacade.DesativarAtivarLivro(logDesativacaoLivro);
        return "redirect:/livros/lista-livro";
    }

    @GetMapping("/retornar-estoque-troca/{id}")
    public String RetornarEstoqueTroca(@PathVariable("id") Integer id){
        Compra compra = compraFacade.getCompra(id);
        livroFacade.retornarEstoque(compra);
        compra.setStatus(StatusEnum.TROCADO);
        compraFacade.cadastrar(compra);
        compraFacade.cadastrarCupomTroca(compra);
        return "redirect:/compras/detalhes-pedido-admin/" + id + "";
    }

    @GetMapping("/excluir-estoque-troca/{id}")
    public String ExcluirEstoqueTroca(@PathVariable("id") Integer id){
        Compra compra = compraFacade.getCompra(id);
        compra.setStatus(StatusEnum.TROCADO);
        compraFacade.cadastrar(compra);
        compraFacade.cadastrarCupomTroca(compra);
        return "redirect:/compras/detalhes-pedido-admin/" + id + "";
    }
}
