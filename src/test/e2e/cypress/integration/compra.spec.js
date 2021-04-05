/// <reference types="Cypress" />
context('Todo tests', () => {
    it('Adicionar itens ao carrinho', () => {
        cy.visit('/clientes/perfil-cliente/1');
        cy.get('.tabela > tbody > :nth-child(1) > td > a').click();
        cy.get(':nth-child(1) > .botao-acao > .botao').click();
        cy.get('#quantidade').type("2");
        cy.get('.compra > .div-botao > .botao').click();
        cy.get(':nth-child(4) > .botao').click();
        cy.get(':nth-child(2) > .botao-acao > .botao').click();
        cy.get('#quantidade').type("1");
        cy.get('.compra > .div-botao > .botao').click();
        cy.get(':nth-child(4) > .botao').click();
        cy.get('[href="/compras/carrinho-compras/1"]').click();
    });

    it('Realizar compra', () => {
        cy.get('[href="/compras/pedido/1"]').click();
        cy.get('#enderecoEntrega2').click();
        cy.get('#cartoes1').click();
        cy.get('#cupons2').click();
        cy.get('#cupons3').click();
        cy.get('.botao').click();
    });

    it('Mudar status', () => {
        cy.visit('/compras/lista-venda');
        cy.get(':nth-child(31) > .botao-acao > .botao').click();
        cy.get('#status').select("Aprovada");
        cy.get('#botao').click();
        cy.visit('/compras/lista-compra/1');
        cy.visit('/compras/lista-venda');
        cy.get(':nth-child(31) > .botao-acao > .botao').click();
        cy.get('#status').select("Em transporte");
        cy.get('#botao').click();
        cy.visit('/compras/lista-compra/1');
        cy.visit('/compras/lista-venda');
        cy.get(':nth-child(31) > .botao-acao > .botao').click();
        cy.get('#status').select("Entregue");
        cy.get('#botao').click();
        cy.visit('/compras/lista-compra/1');
        cy.get(':nth-child(29) > .botao-acao > .botao').click();
    });

    it('Realizar troca', () => {
        cy.get('#troca').click();
        cy.get('#compraLivros1').click();
        cy.get('.botao').click();
        cy.visit('/compras/lista-compra/1');
        cy.visit('/compras/lista-venda');
        cy.get(':nth-child(33) > .botao-acao > .botao').click();
        cy.get('#status').select("Trocado");
        cy.get('#botao').click();
        cy.visit('/compras/lista-venda');
        cy.visit('/compras/lista-compra/1');
        cy.visit('/compras/cupons/1');
    })
});