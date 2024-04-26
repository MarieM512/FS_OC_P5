import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterTestingModule, } from '@angular/router/testing';
import { expect } from '@jest/globals'; 
import { SessionService } from '../../../../services/session.service';

import { DetailComponent } from './detail.component';
import { By } from '@angular/platform-browser';
import { Session } from '../../interfaces/session.interface';
import { of } from 'rxjs';
import { SessionApiService } from '../../services/session-api.service';
import { Router } from '@angular/router';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { TeacherService } from 'src/app/services/teacher.service';


describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>; 
  let router: Router;

  const mockSession: Session = {
    name: 'Session 1',
    date: new Date(),
    description: 'Description',
    users: Array(10).fill({}),
    createdAt: new Date(),
    updatedAt: new Date(),
    teacher_id: 1,
  };

  const mockSessionApiService = {
    detail: jest.fn().mockReturnValue(of(mockSession)),
    delete: jest.fn().mockReturnValue(of({})),
    participate: jest.fn().mockImplementation(() => of(undefined)),
    unParticipate: jest.fn().mockImplementation(() => of(undefined))
  };

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  }

  const mockSnackBar = {
    open: jest.fn().mockImplementation()
  }

  const mockTeacher = {
    id: 1,
    lastName: 'Plaza',
    firstName: 'Steph',
    createdAt: new Date(),
    updatedAt: new Date()
  }

  const mockTeacherService = {
    detail: jest.fn().mockImplementation(() => of(mockTeacher))
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule
      ],
      declarations: [DetailComponent], 
      providers: [
        { provide: SessionService, useValue: mockSessionService }, 
        { provide: SessionApiService, useValue: mockSessionApiService },
        { provide: MatSnackBar, useValue: mockSnackBar },
        { provide: TeacherService, useValue: mockTeacherService }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
      .compileComponents();

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(mockTeacherService.detail).toHaveBeenCalled();
  });

  it('should display delete button when user is an admin', () => {
    expect(
      fixture.debugElement
        .queryAll(By.css('button'))
        .find((button) => button.nativeElement.textContent.includes('delete'))
    ).toBeTruthy();
  })

  it('shouldn\'t display delete button when user is an admin', () => {
    component.isAdmin = false
    fixture.detectChanges();

    expect(
      fixture.debugElement
        .queryAll(By.css('button'))
        .find((button) => button.nativeElement.textContent.includes('delete'))
    ).toBeFalsy();
  })

  it('should display information about session', () => {
    const sessionNameElement = fixture.debugElement.query(By.css('h1')).nativeElement;
    expect(sessionNameElement.textContent).toContain('Session 1');

    const sessionDescriptionElement = fixture.debugElement.query(By.css('.description')).nativeElement;
    expect(sessionDescriptionElement.textContent).toContain('Description');
  })

  it('should delete a session', () => {
    const routerSpy = jest.spyOn(router, 'navigate').mockImplementation(async () => true);

    component.delete();

    expect(mockSessionApiService.delete).toHaveBeenCalled();
    expect(mockSnackBar.open).toHaveBeenCalled();
    expect(routerSpy).toHaveBeenCalledWith(['sessions']);
  })

  it('should go back', () => {
    const spy = jest.spyOn(window.history, 'back').mockImplementation(() => {});

    component.back();

    expect(spy).toHaveBeenCalled();
  })

  it('should participate', () => {
    component.sessionId = '1';
    component.userId = '2';

    component.participate();

    expect(mockSessionApiService.participate).toHaveBeenCalledWith('1', '2');
    expect(component.session).toEqual(mockSession);
  })

  it('should unparticipate', () => {
    component.sessionId = '1';
    component.userId = '2';

    component.unParticipate();

    expect(mockSessionApiService.unParticipate).toHaveBeenCalledWith('1', '2');
    expect(component.session).toEqual(mockSession);
  })
});

