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
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteFacade clienteFacade;

    @GetMapping("/add-cliente")
    public String mostraFormularioCadastro(Cliente cliente) {
        return "cadastro-cliente";
    }

    @PostMapping("/salvar-cliente")
    public String salvarCliente(Cliente cliente) {
        try{
            clienteFacade.cadastrar(cliente);
            return "redirect:/clientes/perfil-cliente/" + cliente.getId() + "";
        } catch (Exception e) {
            log.error("Falha ao salvar cliente.", e);
            return "Falha ao salvar cliente";
        }
    }

    @GetMapping("/perfil-cliente/{id}")
    public String mostraPerfilCliente(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteFacade.getCliente(id);
        model.addAttribute("cliente", cliente);
        return "perfil-cliente";
    }

    @GetMapping("/editar-senha/{id}")
    public String EditSenha(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteFacade.getCliente(id);
        model.addAttribute("cliente", cliente);
        return "update-senha";
    }

    @PostMapping("/atualizar-senha/{id}")
    public String atualizarSenha(Cliente clienteForm, @PathVariable("id") Integer id) {
        try {
            String senha = clienteForm.getSenha();
            Cliente cliente = clienteFacade.getCliente(id);
            clienteFacade.alterarSenha(cliente, senha);
            return "redirect:/clientes/perfil-cliente/" + cliente.getId() + "";
        } catch (Exception e) {
            log.error("Falha ao alterar senha.", e);
            return "Falha ao alterar senha.";
        }
    }

    @GetMapping("/editar-endereco/{id}")
    public String EditEndereco(@PathVariable("id") Integer id, Model model) {
        Endereco endereco = clienteFacade.pegarEndereco(id);
        model.addAttribute("endereco", endereco);
        return "update-endereco";
    }

    @PostMapping("/atualizar-endereco/{id}/{clienteId}")
    public String atualizarEndereco(Endereco endereco, @PathVariable("id") Integer id, @PathVariable("clienteId") Integer clienteId) {
        try {
            Cliente cliente = clienteFacade.getCliente(clienteId);
            endereco.setId(id);
            endereco.setCliente(cliente);
            clienteFacade.alterarDados(endereco);
            return "redirect:/clientes/perfil-cliente/" + endereco.getCliente().getId() + "";
        } catch (Exception e) {
            log.error("Falha ao atualizar endereco.", e);
            return "Falha ao atualizar endereco.";
        }
    }

    @GetMapping("/editar-dados/{id}")
    public String EditDadosPessoais(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteFacade.getCliente(id);
        model.addAttribute("cliente", cliente);
        return "update-dados-pessoais";
    }

    @PostMapping("/atualizar-dados/{id}")
    public String atualizarDados(Cliente clienteForm, @PathVariable("id") Integer id) {
        try {
            Cliente cliente = clienteFacade.getCliente(id);
            clienteForm.setId(id);
            clienteForm.setDataCadastro(cliente.getDataCadastro());
            clienteForm.setSenha(cliente.getSenha());
            clienteFacade.alterarDados(clienteForm);
            return "redirect:/clientes/perfil-cliente/" + clienteForm.getId() + "";
        } catch (Exception e) {
            log.error("Falha ao salvar cliente.", e);
            return "Falha ao salvar cliente.";
        }
    }

    @GetMapping("/editar-cartao/{id}")
    public String EditCartao(@PathVariable("id") Integer id, Model model) {
        Cartao cartao = clienteFacade.pegarCartao(id);
        model.addAttribute("cartao", cartao);
        return "update-cartao";
    }

    @PostMapping("/atualizar-cartao/{id}/{clienteId}")
    public String atualizarCartao(Cartao cartao, @PathVariable("id") Integer id, @PathVariable("clienteId") Integer clienteId) {
        try {
            Cliente cliente = clienteFacade.getCliente(clienteId);
            cartao.setId(id);
            cartao.setCliente(cliente);
            clienteFacade.alterarDados(cartao);
            return "redirect:/clientes/perfil-cliente/" + cartao.getCliente().getId() + "";
        } catch (Exception e) {
            log.error("Falha ao atualizar cartão.", e);
            return "Falha ao atualizar cartão.";
        }
    }

    @GetMapping("/lista-cliente")
    public String mostraListaClientes(Model model) {
        model.addAttribute("clientes", clienteFacade.listarTodos());
        return "lista-cliente";
    }

    @GetMapping("/inativar-ativar/{id}")
    public String inativarCliente(@PathVariable("id") Integer id){
        try {
            Cliente cliente = clienteFacade.getCliente(id);
            clienteFacade.excluir(cliente);
            return "redirect:/clientes/lista-cliente";
        } catch (Exception e) {
            log.error("Falha ao inativar.", e);
            return "Falha ao inativar cliente.";
        }
    }

    @GetMapping("/add-endereco/{clienteId}")
    public String addEnd(Endereco endereco, @PathVariable("clienteId") Integer clienteId, Model model) {
        Cliente cliente = clienteFacade.getCliente(clienteId);
        model.addAttribute("cliente", cliente);
        return "add-endereco";
    }

    @GetMapping("/add-endereco-carrinho/{clienteId}")
    public String addEndCarrinho(Endereco endereco, @PathVariable("clienteId") Integer clienteId, Model model) {
        Cliente cliente = clienteFacade.getCliente(clienteId);
        model.addAttribute("cliente", cliente);
        return "add-endereco-carrinho";
    }

    @GetMapping("/cadastrar-endereco/{clienteId}")
    public String novoEndereco(Endereco endereco, @PathVariable("clienteId") Integer clienteId, Model model) {
        Cliente cliente = clienteFacade.getCliente(clienteId);
        endereco.setCliente(cliente);
        clienteFacade.alterarDados(endereco);
        model.addAttribute("cliente", cliente);
        return "redirect:/clientes/perfil-cliente/" + cliente.getId() + "";
    }

    @GetMapping("/cadastrar-endereco-carrinho/{clienteId}")
    public String novoEnderecoCarrinho(Endereco endereco, @PathVariable("clienteId") Integer clienteId, Model model) {
        Cliente cliente = clienteFacade.getCliente(clienteId);
        endereco.setCliente(cliente);
        clienteFacade.alterarDados(endereco);
        model.addAttribute("cliente", cliente);
        return "redirect:/compras/pedido/" + cliente.getId() + "";
    }

    @GetMapping("/add-cartao/{clienteId}")
    public String addCartao(Cartao cartao, @PathVariable("clienteId") Integer clienteId, Model model) {
        Cliente cliente = clienteFacade.getCliente(clienteId);
        model.addAttribute("cliente", cliente);
        return "add-cartao";
    }

    @GetMapping("/add-cartao-carrinho/{clienteId}")
    public String addCartaoCarrinho(Cartao cartao, @PathVariable("clienteId") Integer clienteId, Model model) {
        Cliente cliente = clienteFacade.getCliente(clienteId);
        model.addAttribute("cliente", cliente);
        return "add-cartao-carrinho";
    }

    @GetMapping("/cadastrar-cartao/{clienteId}")
    public String novoCartao(Cartao cartao, @PathVariable("clienteId") Integer clienteId, Model model) {
        Cliente cliente = clienteFacade.getCliente(clienteId);
        cartao.setCliente(cliente);
        cartao.setPreferencial(false);
        clienteFacade.alterarDados(cartao);
        model.addAttribute("cliente", cliente);
        return "redirect:/clientes/perfil-cliente/" + cliente.getId() + "";
    }

    @GetMapping("/cadastrar-cartao-carrinho/{clienteId}")
    public String novoCartaoCarrinho(Cartao cartao, @PathVariable("clienteId") Integer clienteId, Model model) {
        Cliente cliente = clienteFacade.getCliente(clienteId);
        cartao.setCliente(cliente);
        cartao.setPreferencial(false);
        clienteFacade.alterarDados(cartao);
        model.addAttribute("cliente", cliente);
        return "redirect:/compras/pedido/" + cliente.getId() + "";
    }

    @GetMapping("/alterar-cartao-preferencial/{id}")
    public String atualizarDados(@PathVariable("id") Integer id) {
        try {
            Cartao cartao = clienteFacade.pegarCartao(id);
            Cliente cliente = cartao.getCliente();
            clienteFacade.alterarCartaoPreferencial(cliente, cartao);
            return "redirect:/clientes/perfil-cliente/" + cliente.getId() + "";
        } catch (Exception e) {
            log.error("Falha ao salvar cliente.", e);
            return "Falha ao salvar cliente.";
        }
    }
}
