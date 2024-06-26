// ***********************************************
// This example namespace declaration will help
// with Intellisense and code completion in your
// IDE or Text Editor.
// ***********************************************
// declare namespace Cypress {
//   interface Chainable<Subject = any> {
//     customCommand(param: any): typeof customCommand;
//   }
// }
//
// function customCommand(param: any): void {
//   console.warn(param);
// }
//
// NOTE: You can use it like so:
// Cypress.Commands.add('customCommand', customCommand);
//
// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add("login", (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add("drag", { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add("dismiss", { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite("visit", (originalFn, url, options) => { ... })
declare namespace Cypress {
    interface Chainable {
      login(admin: boolean, sessions: boolean): typeof login
    }
}

const mockSession = {
    id: 1,
    name: 'Session 1',
    date: new Date(),
    description: 'Description',
    users: [1, 2, 3],
    createdAt: new Date(),
    updatedAt: new Date(),
    teacher_id: 1,
}

function login(admin: boolean, sessions: boolean): void {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', { 
        id: 1,
        username: 'Johnny',
        firstName: 'John',
        lastName: 'Doe',
        admin: admin
     }).as('login')

    if (sessions) {
        cy.intercept('GET', '/api/session', [mockSession]).as('sessions')
    } else {
        cy.intercept('GET', '/api/session', []).as('sessions')
    }

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')
}

Cypress.Commands.add('login', login)