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
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private LivroFacade livroFacade;
    @Autowired
    private ClienteFacade clienteFacade;
    @Autowired
    private CompraFacade compraFacade;

    @GetMapping("/carrinho-compras/{clienteId}")
    public String carrinhoCompras(@PathVariable("clienteId") Integer clienteId, Model model, CompraLivro compraLivro){
        Cliente cliente = clienteFacade.getCliente(clienteId);
        model.addAttribute("cliente", cliente);
        return "carrinho";
    }

    @GetMapping("/cupons/{clienteId}")
    public String listaCupom(@PathVariable("clienteId") Integer clienteId, Model model){
        Cliente cliente = clienteFacade.getCliente(clienteId);
        model.addAttribute("cliente", cliente);
        return "cupom";
    }

    @GetMapping("/detalhes-pedido/{id}")
    public String detalhesPedido(@PathVariable("id") Integer id, Model model){
        Compra compra = compraFacade.getCompra(id);
        model.addAttribute("compra", compra);
        return "detalhes-pedido";
    }

    @GetMapping("/detalhes-pedido-admin/{id}")
    public String detalhesPedidoAdmin(@PathVariable("id") Integer id, Model model){
        Compra compra = compraFacade.getCompra(id);
        model.addAttribute("compra", compra);
        return "detalhes-pedido-admin";
    }

    @GetMapping("/lista-compra/{clienteId}")
    public String listaCompra(@PathVariable("clienteId") Integer clienteId, Model model){
        Cliente cliente = clienteFacade.getCliente(clienteId);
        model.addAttribute("cliente", cliente);
        return "lista-compra";
    }

    @GetMapping("/lista-venda")
    public String listaVenda(Model model){
        model.addAttribute("compras", compraFacade.listarTodas());
        return "lista-venda";
    }

    @GetMapping("/lista-troca")
    public String listaTroca(){ return "lista-troca"; }

    @GetMapping("/pedido/{clienteId}")
    public String pedido(@PathVariable("clienteId") Integer clienteId, Model model, Compra compra){
        Cliente cliente = clienteFacade.getCliente(clienteId);
        model.addAttribute("cliente", cliente);
        return "pedido";
    }

    @GetMapping("/add-pedido/{clienteId}")
    public String addPedido(@PathVariable("clienteId") Integer clienteId, Compra compra){
        try{
            Cliente cliente = clienteFacade.getCliente(clienteId);
            compra.setCliente(cliente);
            compra.setStatus(StatusEnum.PROCESSAMENTO);
            compraFacade.cadastrar(compra);
            compraFacade.cadastrarDependencias(compra);
            return "redirect:/compras/lista-compra/" + cliente.getId() + "";
        } catch (Exception e) {
            log.error("Falha ao salvar compra.", e);
            return "Falha ao salvar compra";
        }
    }

    @GetMapping("/troca/{id}")
    public String troca(@PathVariable("id") Integer id, Model model){
        Compra compra = compraFacade.getCompra(id);
        model.addAttribute("compra", compra);
        return "troca";
    }

    @GetMapping("/solicitar-troca/{id}")
    public String solicitarTroca(@PathVariable("id") Integer id, Compra compraForm){
        compraFacade.gerarTroca(compraForm, id);
        return "redirect:/compras/detalhes-pedido/" + id + "";
    }

    @GetMapping("/add-carrinho/{id}/{clienteId}")
    public String addCarrinho(CompraLivro compraLivro, @PathVariable("id") Integer id, @PathVariable("clienteId") Integer clienteId){
        try{
            Livro livro = livroFacade.getLivro(id);
            Cliente cliente = clienteFacade.getCliente(clienteId);
            compraLivro.setLivro(livro);
            compraLivro.setCliente(cliente);
            compraFacade.cadastrarItem(compraLivro);
            return "redirect:/livros/detalhes-livro/" + livro.getId() + "/" + cliente.getId() + "";
        } catch (Exception e) {
            log.error("Falha ao salvar item no carrinho.", e);
            return "Falha ao salvar item no carrinho.";
        }
    }

    @GetMapping("/editar-quantidade/{id}/{clienteId}")
    public String editarQuantidade( @PathVariable("id") Integer id, @PathVariable("clienteId") Integer clienteId, CompraLivro compraLivro){
        try{
            Livro livro = livroFacade.getLivro(id);
            Cliente cliente = clienteFacade.getCliente(clienteId);
            compraLivro.setLivro(livro);
            compraLivro.setCliente(cliente);
            compraFacade.cadastrarItem(compraLivro);
            return "redirect:/compras/carrinho-compras/" + cliente.getId() + "";
        } catch (Exception e) {
            log.error("Falha ao salvar item no carrinho.", e);
            return "Falha ao salvar item no carrinho.";
        }
    }

    @GetMapping("/editar-status/{id}")
    public String editarStatus(@PathVariable("id") Integer id, Compra compraForm){
        Compra compra = compraFacade.getCompra(id);
        compra.setStatus(compraForm.getStatus());
        compraFacade.cadastrar(compra);
        if(compra.getStatus().getDescricao() == "trocado"){
            compraFacade.cadastrarCupomTroca(compra);
        }
        return "redirect:/compras/detalhes-pedido-admin/" + id + "";
    }

    @GetMapping("/excluir-item-carrinho/{id}/{clienteId}")
    public String excluirItem(@PathVariable("id") Integer id, @PathVariable("clienteId") Integer clienteId){
        CompraLivro compraLivro = compraFacade.getCompraLivro(id);
        Cliente cliente = clienteFacade.getCliente(clienteId);
        compraFacade.excluir(compraLivro);
        return "redirect:/compras/carrinho-compras/" + cliente.getId() + "";
    }
}
