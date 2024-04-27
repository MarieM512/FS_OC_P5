describe('Session spec', () => {

    const mockSession = {
        id: 1,
        name: 'Session 1',
        date: new Date(),
        description: 'Description',
        users: [1, 2, 3],
        createdAt: new Date(),
        updatedAt: new Date(),
        teacher_id: 1,
      };

    it('Create a session successfull', () => {
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
      
          cy.intercept(
            {
              method: 'GET',
              url: '/api/session',
            },
            []).as('session')

            cy.intercept(
                {
                  method: 'GET',
                  url: '/api/teacher',
                },
                [
                    {
                        id: 1,
                        lastName: 'Smith',
                        firstName: 'Will',
                        createdAt: new Date(),
                        updatedAt: new Date()
                    }
                ]).as('teacher')

                cy.intercept('POST', 'api/session', { mockSession })
      
          cy.get('input[formControlName=email]').type("yoga@studio.com")
          cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
      
          cy.url().should('include', '/sessions')

          cy.get(':button').contains('Create').click()

          cy.url().should('include', '/sessions/create')

          cy.get('input[formControlName=name]').type('Sport')
          cy.get('input[formControlName=date]').type('2024-04-27')
          cy.get('mat-select[formControlName=teacher_id]').click().get('mat-option').contains('Smith').click()
          cy.get('[formControlName=description]').type('Outside session')

          cy.get(':button').contains('Save').click()
          cy.url().should('include', '/sessions')
          cy.contains('Session created !').should('be.visible');
    })

    it('Display session when admin', () => {
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

    cy.intercept('GET', '/api/session', [mockSession]).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')

    cy.get('mat-card-title').contains(mockSession.name)
    cy.get(':button').contains('Create').should('be.visible')
    cy.get(':button').contains('Edit').should('be.visible')
    })

    it('Display session when no admin', () => {
        cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false
      },
    })

    cy.intercept('GET', '/api/session', [mockSession]).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')

    cy.get('mat-card-title').contains(mockSession.name)
    cy.get(':button').contains('Create').should('not.exist')
    cy.get(':button').contains('Edit').should('not.exist')
    })

    it('Display detail about session when admin', () => {
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
          url: '/api/teacher/1',
        },
        
            {
                id: 1,
                lastName: 'Smith',
                firstName: 'Will',
                createdAt: new Date(),
                updatedAt: new Date()
            }
        ).as('teacher')

    cy.intercept('GET', 'api/session/1', { body: {id: 1,
        name: 'Session 1',
        description: 'Outside session',
        date: new Date(),
        createdAt: new Date(),
        updatedAt: new Date(),
        teacher_id: 1,
        users: [1, 2, 3]} }).as('session')

    cy.intercept('GET', '/api/session', [mockSession]).as('sessions')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')

    cy.get(':button').contains('Detail').click()

    cy.url().should('include', 'sessions/detail/1')

    cy.get('h1').contains(mockSession.name)
    cy.get('.description').contains(mockSession.description)

    cy.get('mat-icon').contains('delete').should('be.visible')

    })

    it('Display detail about session when no admin and unparticipate', () => {
        cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false
      },
    })

    cy.intercept(
        {
          method: 'GET',
          url: '/api/teacher/1',
        },
        
            {
                id: 1,
                lastName: 'Smith',
                firstName: 'Will',
                createdAt: new Date(),
                updatedAt: new Date()
            }
        ).as('teacher')

    cy.intercept('GET', 'api/session/1', { body: {id: 1,
        name: 'Session 1',
        description: 'Outside session',
        date: new Date(),
        createdAt: new Date(),
        updatedAt: new Date(),
        teacher_id: 1,
        users: [1, 2, 3]} }).as('session')


    cy.intercept('DELETE', 'api/session/1/participate/1', {

    }).as('delete')

    cy.intercept('GET', '/api/session', [mockSession]).as('sessions')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')

    cy.get(':button').contains('Detail').click()

    cy.url().should('include', 'sessions/detail/1')

    cy.get('h1').contains(mockSession.name)
    cy.get('.description').contains(mockSession.description)

    cy.get('mat-icon').contains('delete').should('not.exist')

    cy.get('mat-icon').contains('person_remove').should('be.visible').click()


    })

    it('Display detail about session when no admin and participate', () => {
        cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false
      },
    })

    cy.intercept(
        {
          method: 'GET',
          url: '/api/teacher/1',
        },
        
            {
                id: 1,
                lastName: 'Smith',
                firstName: 'Will',
                createdAt: new Date(),
                updatedAt: new Date()
            }
        ).as('teacher')

    cy.intercept('GET', 'api/session/1', { body: {id: 1,
        name: 'Session 1',
        description: 'Outside session',
        date: new Date(),
        createdAt: new Date(),
        updatedAt: new Date(),
        teacher_id: 1,
        users: [2, 3]} }).as('session')


    cy.intercept('POST', 'api/session/1/participate/1', {

    }).as('participate')

    cy.intercept('GET', '/api/session', [mockSession]).as('sessions')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')

    cy.get(':button').contains('Detail').click()

    cy.url().should('include', 'sessions/detail/1')

    cy.get('h1').contains(mockSession.name)
    cy.get('.description').contains(mockSession.description)

    cy.get('mat-icon').contains('delete').should('not.exist')

    cy.get('mat-icon').contains('person_add').should('be.visible').click()


    })

    it('Delete session', () => {
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
          url: '/api/teacher/1',
        },
        
            {
                id: 1,
                lastName: 'Smith',
                firstName: 'Will',
                createdAt: new Date(),
                updatedAt: new Date()
            }
        ).as('teacher')

    cy.intercept('GET', 'api/session/1', { body: {id: 1,
        name: 'Session 1',
        description: 'Outside session',
        date: new Date(),
        createdAt: new Date(),
        updatedAt: new Date(),
        teacher_id: 1,
        users: [1, 2, 3]} }).as('session')

    cy.intercept('GET', '/api/session', [mockSession]).as('sessions')

    cy.intercept('DELETE', '/api/session/1', {}).as('delete session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')

    cy.get(':button').contains('Detail').click()

    cy.url().should('include', 'sessions/detail/1')

    cy.get('h1').contains(mockSession.name)
    cy.get('.description').contains(mockSession.description)

    cy.get('mat-icon').contains('delete').click()

    cy.contains('Session deleted !').should('be.visible');

    cy.url().should('include', 'sessions')

    })

    it('Edit session', () => {
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
          url: '/api/teacher/1',
        },
        
            {
                id: 1,
                lastName: 'Smith',
                firstName: 'Will',
                createdAt: new Date(),
                updatedAt: new Date()
            }
        ).as('teacher')

        cy.intercept(
            {
              method: 'GET',
              url: '/api/teacher',
            },
            [
                {
                    id: 1,
                    lastName: 'Smith',
                    firstName: 'Will',
                    createdAt: new Date(),
                    updatedAt: new Date()
                }
            ]).as('teacher')

    cy.intercept('GET', 'api/session/1', { body: {id: 1,
        name: 'Session 1',
        description: 'Outside session',
        date: new Date(),
        createdAt: new Date(),
        updatedAt: new Date(),
        teacher_id: 1,
        users: [1, 2, 3]} }).as('session')

    cy.intercept('GET', '/api/session', [mockSession]).as('sessions')

    cy.intercept('PUT', '/api/session/1', {mockSession}).as('update session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')

    cy.get(':button').contains('Edit').click()

    cy.url().should('include', 'sessions/update/1')

    cy.get('h1').contains('Update session')
    cy.get('input[formControlName=name]').clear()
    cy.get('input[formControlName=name]').type("Session with Will")

    cy.get(':button').contains('Save').click()

    cy.contains('Session updated !').should('be.visible')

    cy.url().should('include', 'sessions')

    })

    

  });