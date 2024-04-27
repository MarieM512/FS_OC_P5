describe('Logout spec', () => {
    it('Logout successfull', () => {
        cy.login(true, false)

        cy.get('.link').contains('Logout').click()

        cy.url().should('include', '')
    })
  });