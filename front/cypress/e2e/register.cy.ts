describe('Register spec', () => {
    it('Register successfull', () => {
        cy.visit('/register')

        cy.intercept('POST', '/api/auth/register', {}).as('register')
  
        cy.get('input[formControlName=firstName]').type("John")
        cy.get('input[formControlName=lastName]').type("Doe")
        cy.get('input[formControlName=email]').type("test@oc.com")
        cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}`)
  
        cy.url().should('include', '/login')
    })

    it('Should disable submit button when empty field', () => {
        cy.visit('/register')
    
        cy.get('input[formControlName=email').type("yoga@studio.com")
        cy.get('input[formControlName=password').type("test!1234")

        cy.get(':button').should('be.disabled')
    
        cy.get('input[formControlName=firstName').type("John")
        cy.get('input[formControlName=lastName').type("Doe")

        cy.get(':button').should('be.not.disabled')
    })
  });