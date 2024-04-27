describe('Account spec', () => {
    it('Ge me admin successfull', () => {
        cy.visit('/login')

        cy.intercept('POST', '/api/auth/login', {
            body: {
              id: 1,
              username: 'Johnny',
              firstName: 'John',
              lastName: 'Doe',
              admin: true
            },
          })

          cy.intercept('GET', '/api/user/1', {
            id: 1,
            email: 'test@oc.com',
            lastName: 'Doe',
            firstName: 'John',
            admin: true,
            password: 'password',
            createdAt: new Date(),
            updatedAt: new Date()
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

          cy.get('.link').contains('Account').click()

          cy.url().should('include', '/me')

          cy.get('p').contains('test@oc.com')
          cy.get('.my2').contains('You are admin')
    })

    it('Ge me no admin successfull', () => {
        cy.visit('/login')

        cy.intercept('POST', '/api/auth/login', {
            body: {
              id: 1,
              username: 'Johnny',
              firstName: 'John',
              lastName: 'Doe',
              admin: false
            },
          })

          cy.intercept('GET', '/api/user/1', {
            id: 1,
            email: 'test@oc.com',
            lastName: 'Doe',
            firstName: 'John',
            admin: false,
            password: 'password',
            createdAt: new Date(),
            updatedAt: new Date()
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

          cy.get('.link').contains('Account').click()

          cy.url().should('include', '/me')

          cy.get('p').contains('test@oc.com')
          cy.get(':button').contains('Detail')
    })
  });