/// <reference types="Cypress" />
context('Todo tests', () => {
    it('Adicionar itens ao carrinho', () => {
        cy.visit('/clientes/perfil-cliente/1');
        cy.get('.tabela > tbody > :nth-child(1) > td > a').click();
        cy.get(':nth-child(1) > .botao-acao > .botao').click();
        cy.get('#quantidade').type("2");
        cy.get('#add-carrinho').click();
        cy.get('#voltar').click();
    });

    it('Impedir que item seja adicionado novamente', () => {
        cy.visit('/livros/principal/1');
        cy.get(':nth-child(1) > .botao-acao > .botao').click();
        cy.get('#quantidade').type("1");
        cy.get('#add-carrinho').click();
        cy.get('#voltar').click();
    });

    it('Impedir que item seja adicionado em quantidade maior que o estoque', () => {
        cy.visit('/livros/principal/1');
        cy.get(':nth-child(2) > .botao-acao > .botao').click();
        cy.get('#quantidade').type("7");
        cy.get('.compra > .div-botao > .botao').click();
        cy.get('#quantidade').clear();
        cy.get('#quantidade').type("1");
        cy.get('#add-carrinho').click();
        cy.get('#voltar').click();
    });

    it('Cadastrar novo cartão na tela de pedido', () => {
        cy.visit('/livros/principal/1');
        cy.get('[href="/compras/carrinho-compras/1"]').click();
        cy.get('[href="/compras/pedido/1"]').click();
        cy.get(':nth-child(3) > .botao-editar > .editar').click();
        cy.get('#bandeira').select("Visa");
        cy.get('#num_card').type("7785336715678924");
        cy.get('#cod_seguranca').type("875");
        cy.get('#nome_card').type("Maria F Santos");
        cy.get('.botao').click();
    });

    it('Impedir que seja passado menos de R$10,00 em cada cartão', () => {
        cy.visit('/compras/pedido/1');
        cy.get('#enderecoEntrega1').click();
        cy.get('#cartoes1').click();
        cy.get('#cartoes2').click();
        cy.get('#cartoes3').click();
        cy.get('.botao').click();
    });

    it('Impedir o uso de cupons em excesso', () => {
        cy.visit('/compras/pedido/1');
        cy.get('#enderecoEntrega1').click();
        cy.get('#cartoes1').click();
        cy.get('#cartoes2').click();
        cy.get('#cupons1').click();
        cy.get('#cupons2').click();
        cy.get('.botao').click();
    });

    it('Realizar compra', () => {
        cy.visit('/compras/pedido/1');
        cy.get('#enderecoEntrega1').click();
        cy.get('#cartoes1').click();
        cy.get('#cartoes2').click();
        cy.get('#cupons1').click();
        cy.get('.botao').click();
    });

    it('Mudar status', () => {
        cy.visit('/compras/lista-venda');
        cy.get(':nth-child(1) > .botao-acao > .botao').click();
        cy.get('#status').select("Aprovada");
        cy.get('#botao').click();
        cy.visit('/compras/lista-compra/1');
        cy.visit('/compras/lista-venda');
        cy.get(':nth-child(1) > .botao-acao > .botao').click();
        cy.get('#status').select("Em transporte");
        cy.get('#botao').click();
        cy.visit('/compras/lista-compra/1');
        cy.visit('/compras/lista-venda');
        cy.get(':nth-child(1) > .botao-acao > .botao').click();
        cy.get('#status').select("Entregue");
        cy.get('#botao').click();
        cy.visit('/compras/lista-compra/1');
        cy.get(':nth-child(1) > .botao-acao > .botao').click();
    });

});