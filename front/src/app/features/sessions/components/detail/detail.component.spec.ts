import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterTestingModule, } from '@angular/router/testing';
import { expect } from '@jest/globals'; 
import { SessionService } from '../../../../services/session.service';

import { DetailComponent } from './detail.component';
import { By } from '@angular/platform-browser';
import { Session } from '../../interfaces/session.interface';
import { of } from 'rxjs';
import { SessionApiService } from '../../services/session-api.service';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';


describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>; 
  let service: SessionService;

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
  };

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
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
      providers: [{ provide: SessionService, useValue: mockSessionService }, { provide: SessionApiService, useValue: mockSessionApiService }],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
      .compileComponents();
      service = TestBed.inject(SessionService);
    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
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
    component.sessionId = '1';
    component.delete();
    expect(mockSessionApiService.delete).toHaveBeenCalledWith('1');
  })
});

