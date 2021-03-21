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
@RequestMapping("/compras")
public class CompraController {

    @GetMapping("/carrinho-compras")
    public String carrinhoCompras(){ return "carrinho"; }

    @GetMapping("/cupons")
    public String listaCupom(){ return "cupom"; }

    @GetMapping("/detalhes-pedido")
    public String detalhesPedido(){ return "detalhes-pedido"; }

    @GetMapping("/detalhes-pedido-admin")
    public String detalhesPedidoAdmin(){ return "detalhes-pedido-admin"; }

    @GetMapping("/lista-compra")
    public String listaCompra(){ return "lista-compra"; }

    @GetMapping("/lista-venda")
    public String listaVenda(){ return "lista-venda"; }

    @GetMapping("/lista-troca")
    public String listaTroca(){ return "lista-troca"; }

    @GetMapping("/pedido")
    public String pedido(){ return "pedido"; }

    @GetMapping("/troca")
    public String troca(){ return "troca"; }
}
