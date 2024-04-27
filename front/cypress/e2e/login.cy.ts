describe('Login spec', () => {
  it('Login successfull', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')
  })

  it('Should display error when wrong password', () => {
    cy.visit('/login')

    cy.get('input[formControlName=email').type("yoga@studio.com")
    cy.get('input[formControlName=password').type(`${"test1234"}{enter}{enter}`)

    cy.get('.error')
  })

  it('Should disable submit button when empty field', () => {
    cy.visit('/login')

    cy.get('input[formControlName=email').type("yoga@studio.com")

    cy.get(':button').should('be.disabled')

    cy.get('input[formControlName=password').type("test1234")

    cy.get(':button').should('be.not.disabled')
  })
});