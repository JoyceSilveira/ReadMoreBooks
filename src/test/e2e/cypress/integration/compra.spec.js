/// <reference types="Cypress" />
context('Todo tests', () => {
    it('Adicionar itens ao carrinho', () => {
        cy.viewport(1348, 879);
        cy.visit('/clientes/perfil-cliente/33');
        cy.get('.tabela > tbody > :nth-child(1) > td > a').click();
        cy.get(':nth-child(1) > .botao-acao > .botao').click();
        cy.get('#quantidade').type("2");
        cy.get('#add-carrinho').click();
        cy.get(':nth-child(3) > .botao-acao > .botao').click();
        cy.get('#quantidade').type("1");
        cy.get('#add-carrinho').click();
    });

    it('Impedir que item seja adicionado novamente', () => {
        cy.viewport(1348, 879);
        cy.visit('/livros/principal/33');
        cy.get(':nth-child(3) > .botao-acao > .botao').click();
        cy.get('#quantidade').type("1");
        cy.get('#add-carrinho').click();
        cy.get('#voltar').click();
    });

    it('Impedir que item seja adicionado em quantidade maior que o estoque', () => {
        cy.viewport(1348, 879);
        cy.visit('/livros/principal/33');
        cy.get(':nth-child(4) > .botao-acao > .botao').click();
        cy.get('#quantidade').type("100");
        cy.get('.compra > .div-botao > .botao').click();
        cy.get('#quantidade').clear();
        cy.get('#quantidade').type("1");
        cy.get('#add-carrinho').click();
    });

    it('Gerenciar carrinho', () => {
        cy.viewport(1348, 879);
        cy.visit('/livros/principal/33');
        cy.get('[href="/compras/carrinho-compras/33"]').click();
        cy.get(':nth-child(3) > [onclick="alterarQuantidade(this)"] > .botao').click();
        cy.get(':nth-child(3) > :nth-child(8) > #quantidade').clear();
        cy.get(':nth-child(3) > :nth-child(8) > #quantidade').type("2");
        cy.get(':nth-child(3) > :nth-child(9) > .botao').click();
        cy.get(':nth-child(2) > :nth-child(10) > .botao').click();
    });

    it('Cadastrar novo cartão na tela de pedido', () => {
        cy.viewport(1348, 879);
        cy.visit('/compras/carrinho-compras/33');
        cy.get('[href="/compras/pedido/33"]').click();
        cy.get(':nth-child(3) > .botao-editar > .editar').click();
        cy.get('#bandeira').select("Visa");
        cy.get('#num_card').type("7785336715678924");
        cy.get('#cod_seguranca').type("875");
        cy.get('#nome_card').type("Maria F Santos");
        cy.get('.botao').click();
    });

    it('Impedir que seja passado menos de R$10,00 em cada cartão', () => {
        cy.viewport(1348, 879);
        cy.visit('/compras/pedido/33');
        cy.get('#enderecoEntrega1').click();
        cy.get('#cartoes1').click();
        cy.get('#cartoes2').click();
        cy.get('#cartoes3').click();
        cy.get('.botao').click();
    });

    it('Impedir o uso de cupons em excesso', () => {
        cy.viewport(1348, 879);
        cy.visit('/compras/pedido/33');
        cy.get('#enderecoEntrega1').click();
        cy.get('#cartoes1').click();
        cy.get('#cartoes2').click();
        cy.get('#cupons1').click();
        cy.get('#cupons3').click();
        cy.get('.botao').click();
    });

    it('Realizar compra', () => {
        cy.viewport(1348, 879);
        cy.visit('/compras/pedido/33');
        cy.get('#enderecoEntrega1').click();
        cy.get('#cartoes1').click();
        cy.get('#cartoes2').click();
        cy.get('#cupons1').click();
        cy.get('.botao').click();
    });

     it('Mudar status', () => {
        cy.viewport(1348, 879);
        cy.visit('/compras/lista-venda');
        cy.get(':nth-child(1) > .botao-acao > .botao').click();
        cy.get('#status').select("Em transporte");
        cy.get('#botao').click();
        cy.visit('/compras/lista-venda');
        cy.get(':nth-child(1) > .botao-acao > .botao').click();
        cy.get('#status').select("Entregue");
        cy.get('#botao').click();
        cy.visit('/compras/lista-compra/33');
     });

    it('Realizar troca', () =>{
        cy.viewport(1348, 879);
        cy.visit('/compras/lista-compra/33');
        cy.get(':nth-child(1) > .botao-acao > .botao').click();
        cy.get('#troca').click();
        cy.get('#compraLivros1').click();
        cy.get('.botao').click();
        cy.visit('/compras/lista-compra/33');
        cy.visit('/compras/lista-venda');
        cy.get(':nth-child(1) > .botao-acao > .botao').click();
        cy.get('#statusTroca').select("Trocado");
        cy.get('#sim').click();
        cy.visit('/compras/lista-venda');
        cy.visit('/compras/lista-compra/33');
        cy.visit('/compras/cupons/33');
    })

});