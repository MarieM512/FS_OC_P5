import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionApiService } from './session-api.service';
import { HttpClientModule } from '@angular/common/http';

describe('SessionsService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  const mockSessions = [
    {
      id: 1,
      name: 'Session 1',
      date: new Date(),
      description: 'Description',
      users: Array(10).fill({}),
      createdAt: new Date(),
      updatedAt: new Date(),
      teacher_id: 1,
    },
    {
      id: 2,
      name: 'Session 2',
      date: new Date(),
      description: 'Description',
      users: Array(8).fill({}),
      createdAt: new Date(),
      updatedAt: new Date(),
      teacher_id: 1,
    }
  ]

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [SessionApiService]
    });
    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all sessions', done => {
    service.all().subscribe(response => {
      expect(response.length).toEqual(mockSessions.length);
      done();
    });

    const request = httpMock.expectOne('api/session');
    expect(request.request.method).toEqual('GET');
    request.flush(mockSessions);
  });

  it('should get one session', done => {
    service.detail('1').subscribe(response => {
      expect(response.id).toEqual(1);
      expect(response.name).toEqual(mockSessions[0].name);
      done();
    });

    const request = httpMock.expectOne('api/session/1');
    expect(request.request.method).toEqual('GET');
    request.flush(mockSessions[0]);
  });

  it('should delete a session', done => {
    service.delete('1').subscribe(response => {
      expect(response.id).toBeUndefined();
      done();
    });

    const request = httpMock.expectOne('api/session/1');
    expect(request.request.method).toEqual('DELETE');
    request.flush({});
  });

  it('should create a session', done => {
    service.create(mockSessions[0]).subscribe(response => {
      expect(response).toEqual(mockSessions[0])
      done();
    });

    const request = httpMock.expectOne('api/session');
    expect(request.request.method).toEqual('POST');
    request.flush(mockSessions[0]);
  });

  it('should update a session', done => {
    service.update('1', mockSessions[1]).subscribe(response => {
      expect(response).toEqual(mockSessions[1])
      done();
    });

    const request = httpMock.expectOne('api/session/1');
    expect(request.request.method).toEqual('PUT');
    request.flush(mockSessions[1]);
  });

  it('should participate', done => {
    service.participate('1', '1').subscribe(response => {
      expect(response).toEqual({});
      done();
    });

    const request = httpMock.expectOne('api/session/1/participate/1');
    expect(request.request.method).toEqual('POST');
    request.flush({});
  });

  it('should unparticipate', done => {
    service.unParticipate('1', '1').subscribe(response => {
      expect(response).toEqual({});
      done();
    });

    const request = httpMock.expectOne('api/session/1/participate/1');
    expect(request.request.method).toEqual('DELETE');
    request.flush({});
  });
});
