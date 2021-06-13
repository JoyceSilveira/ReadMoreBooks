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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

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
        model.addAttribute("gruposPrecificacao", livroFacade.pegarGruposPrecificacao());
        return "update-livro";
    }

    @GetMapping("/atualizar-livro/{id}")
    public String AtualizarLivro(Livro livroForm, @PathVariable("id") Integer id, RedirectAttributes redirAttrs){
        Livro livro = livroFacade.getLivro(id);
        livroForm.setEstoque(livro.getEstoque());
        String msg = livroFacade.AtualizarDados(livro, livroForm);
        if(msg == null){
            return "redirect:/livros/detalhes-livro-admin/" + id + "";
        } else {
            redirAttrs. addFlashAttribute ( "erro" , msg ) ;
            return "redirect:/livros/editar-livro/" + id + "";
        }
    }

    @GetMapping("/lista-livro")
    public String listaLivro(Model model){
        model.addAttribute("livros", livroFacade.listarTodos());
        return "lista-livro";
    }

    @GetMapping("/add-livro")
    public String mostraFormularioLivro(Livro livro, Model model){
        model.addAttribute("categorias", livroFacade.pegarCategorias());
        model.addAttribute("gruposPrecificacao", livroFacade.pegarGruposPrecificacao());
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
    public String InativarAtivarLivro(@PathVariable("id") Integer id, LogDesativacaoLivro logDesativacaoLivro, RedirectAttributes redirAttrs){
        Livro livro = livroFacade.getLivro(id);
        logDesativacaoLivro.setLivro(livro);
        String msg = livroFacade.DesativarAtivarLivro(logDesativacaoLivro);
        if(msg == null){
            return "redirect:/livros/lista-livro";
        } else {
            redirAttrs. addFlashAttribute ( "erro" , msg ) ;
            return "redirect:/livros/lista-livro";
        }
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

    @GetMapping("/estoque/{id}")
    public String estoque(@PathVariable("id") Integer id, Model model, LogEstoque logEstoque){
        Livro livro = livroFacade.getLivro(id);
        model.addAttribute("livro", livro);
        return "add-estoque";
    }

    @GetMapping("/entrada-estoque/{id}")
    public String entradaEstoque(@PathVariable("id") Integer id, LogEstoque logEstoque, RedirectAttributes redirAttrs ){
        Livro livro = livroFacade.getLivro(id);
        logEstoque.setLivro(livro);
        String msg = livroFacade.darEntradaEstoque(logEstoque);
        if(msg == null){
            return "redirect:/livros/detalhes-livro-admin/" + id + "";
        } else {
            redirAttrs. addFlashAttribute ( "erro" , msg ) ;
            return "redirect:/livros/detalhes-livro-admin/" + id + "";
        }
    }

    @GetMapping("/logs-estoque/{id}")
    public String logsEstoque(@PathVariable("id") Integer id, Model model){
        Livro livro = livroFacade.getLivro(id);
        List<LogEstoque> logsEstoque = livro.getLogsEstoque();
        Collections.sort(logsEstoque);
        model.addAttribute("logsEstoque", logsEstoque);
        return "log-estoque";
    }

    @GetMapping("/logs-inativacao/{id}")
    public String logsInativacao(@PathVariable("id") Integer id, Model model){
        Livro livro = livroFacade.getLivro(id);
        List<LogDesativacaoLivro> logsInativacao = livro.getLogsInativacao();
        Collections.sort(logsInativacao);
        model.addAttribute("logsInativacao", logsInativacao);
        return "log-inativacao-livro";
    }
}
