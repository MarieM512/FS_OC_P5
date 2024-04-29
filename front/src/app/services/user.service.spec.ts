import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { UserService } from './user.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { User } from '../interfaces/user.interface';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  const mockUser: User = {
    id: 1,
    email: "test@oc.com",
    lastName: "Doe",
    firstName: "John",
    admin: true,
    password: "password",
    createdAt: new Date(),
    updatedAt: new Date()
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get user by id', done => {
    service.getById(String(mockUser.id)).subscribe(response => {
      expect(response.email).toEqual(mockUser.email);
      done();
    });

    const request = httpMock.expectOne('api/user/1');
    expect(request.request.method).toEqual('GET');
    request.flush(mockUser);
  });

  it('should delete user', done => {
    service.delete(String(mockUser.id)).subscribe(response => {
      expect(response.id).toBeUndefined();
      done();
    });

    const request = httpMock.expectOne('api/user/1');
    expect(request.request.method).toEqual('DELETE');
    request.flush({});
  });
});
