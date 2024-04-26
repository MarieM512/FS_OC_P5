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
    date: new Date(2024, 3, 6),
    description: 'Première session',
    users: Array(10).fill({}),
    createdAt: new Date(2024, 2, 20),
    updatedAt: new Date(2024, 3, 1),
    teacher_id: 2,
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
});

