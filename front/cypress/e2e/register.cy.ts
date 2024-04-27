describe('Register spec', () => {
    it('Register successfull', () => {
      cy.visit('/register')

      cy.intercept('POST', '/api/auth/register', {})
  
      cy.get('input[formControlName=firstName]').type("John")
      cy.get('input[formControlName=lastName]').type("Doe")
      cy.get('input[formControlName=email]').type("test@oc.com")
      cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}`)
  
      cy.url().should('include', '/login')
    })
  });