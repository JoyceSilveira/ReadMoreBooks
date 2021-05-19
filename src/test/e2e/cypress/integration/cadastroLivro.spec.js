/// <reference types="Cypress" />
context('Todo tests', () => {
    it('Exibir formulário cadastro livro', () => {
        cy.visit('/livros/add-livro');
    });

    it('Adicionar livro 1', () => {
        cy.get('#titulo').type("Harry Potter e a Pedra Filosofal");
        cy.get('#editora').type("Bloomsbury Publishing");
        cy.get('#autor').type("J. K. Rowling");
        cy.get('#ano').type("1997");
        cy.get('#edicao').type("1ª Edição");
        cy.get('#isbn').type("0-7475-3269-9")
        cy.get('#n_paginas').type("223");
        cy.get('#sinopse').type("O livro conta a história de Harry Potter, um órfão criado pelos tios que descobre, em seu décimo primeiro aniversário, que é um bruxo.");
        cy.get('#categorias1').check();
        cy.get('#categorias2').check();
        cy.get('#g_precificação').type("Mais vendidos");
        cy.get('#custo').type("9.0");
        cy.get('#preco').type("12.0");
        cy.get('#cod_barras').type("0000.0000.0000");
        cy.get('#quantidade_init').type("5");
        cy.get('#altura').type("00");
        cy.get('#largura').type("00");
        cy.get('#peso').type("00");
        cy.get('#profundidade').type("00");

        cy.get('.botao').click();
    });

    it('Exibir formulário cadastro livro', () => {
        cy.visit('/livros/add-livro');
    });

    it('Adicionar livro 2', () => {
        cy.get('#titulo').type("As crônicas de Nárnia");
        cy.get('#editora').type("Bloomsbury Publishing");
        cy.get('#autor').type("C. S. Lewis");
        cy.get('#ano').type("1950");
        cy.get('#edicao').type("1ª Edição");
        cy.get('#isbn').type("0-7475-3269-9")
        cy.get('#n_paginas').type("223");
        cy.get('#sinopse').type("Narra a história de quatro crianças humanas: Pedro, Susana, Edmundo e Lúcia Pevensie, que atravessam de um antigo e misterioso guarda-roupa.");
        cy.get('#categorias1').check();
        cy.get('#g_precificação').type("Mais vendidos");
        cy.get('#custo').type("8.0");
        cy.get('#preco').type("10.0");
        cy.get('#cod_barras').type("0000.0000.0000");
        cy.get('#quantidade_init').type("5");
        cy.get('#altura').type("00");
        cy.get('#largura').type("00");
        cy.get('#peso').type("00");
        cy.get('#profundidade').type("00");

        cy.get('.botao').click();
    });

});