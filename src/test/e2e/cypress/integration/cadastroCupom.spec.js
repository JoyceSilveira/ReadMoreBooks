/// <reference types="Cypress" />
context('Todo tests', () => {
    it('Exibir formulário cadastro cupom', () => {
        cy.visit('/compras/add-cupom');
    });

    it('Adicionar cupom', () => {
        cy.get('#nome').type("Cupom teste");
        cy.get('#valor').type("50.0");
        cy.get('#tipo').select("Promocional");

        cy.get('.botao').click();
    });

    it('Exibir formulário cadastro cupom', () => {
        cy.visit('/compras/add-cupom');
    });

    it('Adicionar cupom', () => {
        cy.get('#nome').type("Cupom 2 teste");
        cy.get('#valor').type("100.0");
        cy.get('#tipo').select("Promocional");

        cy.get('.botao').click();
    });

});