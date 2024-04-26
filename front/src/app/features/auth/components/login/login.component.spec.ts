import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';

import { LoginComponent } from './login.component';
import { AuthService } from '../../services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { of, throwError } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { Router } from '@angular/router';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  const mockUser: SessionInformation = {
    token: "string",
    type: "string",
    id: 1,
    username: "Johnny",
    firstName: "John",
    lastName: "Doe",
    admin: true
  }

  const mockAuthService = {
    login: jest.fn().mockReturnValue(of(mockUser)),
    loginFailed: jest.fn().mockReturnValue(throwError(() => new Error('Connection failure')))
  }

  const mockSessionService = {
    logIn: jest.fn()
  }

  const mockRouter = {
    navigate: jest.fn()
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: AuthService, useValue: mockAuthService },
        { provide: SessionService, useValue: mockSessionService },
        { provide: Router, useValue: mockRouter }
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should login and navigate', () => {
    component.submit();

    expect(mockAuthService.login).toHaveBeenCalled();
    expect(mockSessionService.logIn).toHaveBeenCalled();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/sessions']);
  });

  it('should display error when empty field', () => {
    jest.spyOn(mockAuthService, 'login').mockReturnValue(throwError(() => new Error('Connection failure')));
    
    component.submit();
    expect(component.onError).toBe(true);
  })
});
