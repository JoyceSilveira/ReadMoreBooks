/// <reference types="Cypress" />
context('Todo tests', () => {
    it('Exibir formulário cadastro cliente', () => {
        cy.visit('/');
        cy.get('.redirecionamento').click();
    });

    it('Verificar campos obrigatórios', () => {
        cy.get('#nome').type("Maria Feitosa Santos");
        cy.get('#genero').select("Feminino");
        cy.get('#dt_nascimento').type("1968-05-10");
        cy.get('#cpf').type("265798432");
        cy.get('#tel').type("11985632145");
        cy.get('#email').type("maria_f");
        cy.get('#senha').type("Mf@1111");
        cy.get('#rep-senha').type("Mf@1111");

        cy.get('#tp_residencia_end').select("Apartamento");
        cy.get('#tp_logradouro_end').select("Avenida");
        cy.get('#logradouro_end').type("Dr. Arnaldo");
        cy.get('#cep_end').type("08710787");
        cy.get('#numero_end').type("78");
        cy.get('#complemento_end').type("apto 6");
        cy.get('#bairro_end').type("Centro");
        cy.get('#cidade_end').type("Itaquera");
        cy.get(':nth-child(6) > :nth-child(2) > #identificacao').type("Casa");
        cy.get('#obs_end').type("Entregas com o porteiro");

        cy.get('#logradouro').type("Dr. Arnaldo");
        cy.get('#cep').type("0872654");
        cy.get('#numero').type("27");
        cy.get('#complemento');
        cy.get('#bairro').type("Vila Fátima");
        cy.get('#cidade').type("Itaquera");
        cy.get(':nth-child(12) > :nth-child(2) > #identificacao').type("Trabalho");
        cy.get('#obs');

        cy.get('#num_card').type("478565987412032");
        cy.get('#cod_seguranca').type("788");

        cy.get('.botao').click();

        cy.get('#nome_card').type("Maria F Santos");
        cy.get('.botao').click();
    });

    it('Verificar cpf válido', () => {
        cy.get('#cpf').type("10");
        cy.get('.botao').click();
    });

    it('Verificar e-mail válido', () => {
        cy.get('#email').type("@hotmail.com");
        cy.get('.botao').click();
    });

    it('Verificar senha com todos os requisitos', () => {
        cy.get('#senha').type("1");
        cy.get('.botao').click();
    });

    it('Verificar senhas iguais', () => {
        cy.get('#rep-senha').type("1");
        cy.get('.botao').click();
    });

    it('Verificar cep válido', () => {
        cy.get('#cep').type("5");
        cy.get('.botao').click();
    });

    it('Verificar número de cartão válido', () => {
        cy.get('#num_card').type("8");
        cy.get('.botao').click();
    });
});