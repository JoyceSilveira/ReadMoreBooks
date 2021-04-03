/// <reference types="Cypress" />
context('Todo tests', () => {
    it('Carregamento da pagina', () => {
        cy.visit('/');
    });
});