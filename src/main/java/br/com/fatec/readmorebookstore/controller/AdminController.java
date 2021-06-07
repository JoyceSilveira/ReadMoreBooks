package br.com.fatec.readmorebookstore.controller;

import br.com.fatec.readmorebookstore.dominio.Categoria;
import br.com.fatec.readmorebookstore.dominio.Compra;
import br.com.fatec.readmorebookstore.dominio.FiltroPeriodo;
import br.com.fatec.readmorebookstore.dominio.Livro;
import br.com.fatec.readmorebookstore.facade.CompraFacade;
import br.com.fatec.readmorebookstore.facade.LivroFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CompraFacade compraFacade;
    @Autowired
    private LivroFacade livroFacade;

    @GetMapping("/dashboard")
    public String menuAdmin(Model model, FiltroPeriodo filtroPeriodo){
        Map<String, Integer> vendasPorLivro = compraFacade.contarLivrosVendidos();
        Map<String, Integer> vendasPorCategoria = compraFacade.contarCategoriasVendidas();
        List<Livro> livros = livroFacade.listarTodos();
        List<Categoria> categorias = livroFacade.pegarCategorias();
        List<ArrayList> vLivros = new ArrayList<>();
        List<String> tituloLivro = new ArrayList<>();
        tituloLivro.add("Livro");
        tituloLivro.add("Quanto foi vendido");
        vLivros.add((ArrayList) tituloLivro);
        for(int i=0;i<livros.size();i++){
//            model.addAttribute(livros.get(i).getTitulo(), vendasPorLivro.get(livros.get(i).getTitulo()));
            List<String> infoLivro = new ArrayList<>();
            infoLivro.add(livros.get(i).getTitulo());
            infoLivro.add(Integer.toString(vendasPorLivro.get(livros.get(i).getTitulo())));
            vLivros.add((ArrayList) infoLivro);
        }
        model.addAttribute("vLivros", vLivros);
        List<ArrayList> vCategorias = new ArrayList<>();
        List<String> tituloCategoria = new ArrayList<>();
        tituloCategoria.add("Categoria");
        tituloCategoria.add("Quanto foi vendido");
        vCategorias.add((ArrayList) tituloCategoria);
        for(int i=0;i<categorias.size();i++){
//            model.addAttribute(livros.get(i).getTitulo(), vendasPorLivro.get(livros.get(i).getTitulo()));
            List<String> infoCategoria = new ArrayList<>();
            infoCategoria.add(categorias.get(i).getNome());
            infoCategoria.add(Integer.toString(vendasPorCategoria.get(categorias.get(i).getNome())));
            vCategorias.add((ArrayList) infoCategoria);
        }
        model.addAttribute("vCategorias", vCategorias);
        return "dashboard";
    }

    @GetMapping("/consulta-dashboard")
    public String consultaDash(Model model, FiltroPeriodo filtroPeriodo){
        List<Compra> comprasP = compraFacade.filtrarPeriodoCompra(filtroPeriodo);
        Map<String, Integer> vendasPorLivro = compraFacade.contarLivrosVendidosPeriodo(comprasP);
        Map<String, Integer> vendasPorCategoria = compraFacade.contarCategoriasVendidasPeriodo(comprasP);
        List<Livro> livros = livroFacade.listarTodos();
        List<Categoria> categorias = livroFacade.pegarCategorias();
        List<ArrayList> vLivros = new ArrayList<>();
        List<String> tituloLivro = new ArrayList<>();
        tituloLivro.add("Livro");
        tituloLivro.add("Quanto foi vendido");
        vLivros.add((ArrayList) tituloLivro);
        for(int i=0;i<livros.size();i++){
//            model.addAttribute(livros.get(i).getTitulo(), vendasPorLivro.get(livros.get(i).getTitulo()));
            List<String> infoLivro = new ArrayList<>();
            infoLivro.add(livros.get(i).getTitulo());
            infoLivro.add(Integer.toString(vendasPorLivro.get(livros.get(i).getTitulo())));
            vLivros.add((ArrayList) infoLivro);
        }
        model.addAttribute("vLivros", vLivros);
        List<ArrayList> vCategorias = new ArrayList<>();
        List<String> tituloCategoria = new ArrayList<>();
        tituloCategoria.add("Categoria");
        tituloCategoria.add("Quanto foi vendido");
        vCategorias.add((ArrayList) tituloCategoria);
        for(int i=0;i<categorias.size();i++){
//            model.addAttribute(livros.get(i).getTitulo(), vendasPorLivro.get(livros.get(i).getTitulo()));
            List<String> infoCategoria = new ArrayList<>();
            infoCategoria.add(categorias.get(i).getNome());
            infoCategoria.add(Integer.toString(vendasPorCategoria.get(categorias.get(i).getNome())));
            vCategorias.add((ArrayList) infoCategoria);
        }
        model.addAttribute("vCategorias", vCategorias);
//        return "redirect:/admin/dashboard/";
        return "dashboard";
    }

}
