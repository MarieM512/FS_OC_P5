import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;
  const mockUser: SessionInformation = {
    token: "string",
    type: "string",
    id: 1,
    username: "Johnny",
    firstName: "John",
    lastName: "Doe",
    admin: true
  }

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should connected', () => {
    expect(service.isLogged).toBeFalsy();
    expect(service.sessionInformation).toBeUndefined();

    service.logIn(mockUser);

    expect(service.isLogged).toBeTruthy();
    expect(service.sessionInformation).toEqual(mockUser);
  });

  it('should disconnected', () => {
    service.logOut();

    expect(service.isLogged).toBeFalsy();
    expect(service.sessionInformation).toBeUndefined();
  });
});
