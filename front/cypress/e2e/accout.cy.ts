describe('Account spec', () => {

    const userAdmin = {
        id: 1,
        email: 'test@oc.com',
        lastName: 'Doe',
        firstName: 'John',
        admin: true,
        password: 'password',
        createdAt: new Date(),
        updatedAt: new Date()
    }

    const userNoAdmin = {
        id: 1,
        email: 'test@oc.com',
        lastName: 'Doe',
        firstName: 'John',
        admin: false,
        password: 'password',
        createdAt: new Date(),
        updatedAt: new Date()
    }

    it('Get me admin successfull', () => {
        cy.login(true, false)

        cy.intercept('GET', '/api/user/1', userAdmin).as('user')

        cy.get('.link').contains('Account').click()

        cy.url().should('include', '/me')

        cy.get('p').contains('test@oc.com')
        cy.get('.my2').contains('You are admin')
    })

    it('Get me no admin successfull', () => {
        cy.login(false, false)

        cy.intercept('GET', '/api/user/1', userNoAdmin).as('user')

        cy.get('.link').contains('Account').click()

        cy.url().should('include', '/me')

        cy.get('p').contains('test@oc.com')
        cy.get(':button').contains('Detail')
    })
  });