import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';

import { FormComponent } from './form.component';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { Session } from '../../interfaces/session.interface';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let router: Router;

  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  } 

  const mockSession: Session = {
    id: 1,
    name: 'Session 1',
    date: new Date(),
    description: 'Description',
    users: Array(10).fill({}),
    createdAt: new Date(),
    updatedAt: new Date(),
    teacher_id: 1,
  };

  const mockSessionApiService = {
    detail: jest.fn().mockReturnValue(of(mockSession))
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({

      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule, 
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionApiService }
      ],
      declarations: [FormComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // it('should naviagte to sessions when user is not an admin', () => {
  //   const routerSpy = jest.spyOn(router, 'navigate').mockImplementation(async () => true);
  //   mockSessionService.sessionInformation =  {
  //     admin: false
  //   }

  //   component.ngOnInit();

  //   expect(routerSpy).toHaveBeenCalledWith(['/sessions']);
  // })

  it('should retrieve all values if update', () => {
    jest.spyOn(router, 'url', 'get').mockReturnValue('/update');

    component.ngOnInit();

    expect(router.url).toBe('/update');
    expect(component.onUpdate).toBeTruthy();
    expect(mockSessionApiService.detail).toHaveBeenCalled();
    expect(component.sessionForm?.value.name).toEqual(mockSession.name)
  })
});
