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

  let authService: AuthService;
  let sessionService: SessionService;
  let routerTest: Router;

  const mockUser: SessionInformation = {
    token: "string",
    type: "string",
    id: 1,
    username: "Johnny",
    firstName: "John",
    lastName: "Doe",
    admin: true
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [SessionService],
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

    authService = TestBed.inject(AuthService);
    sessionService = TestBed.inject(SessionService);
    routerTest = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display error when empty field', () => {
    jest
      .spyOn(authService, 'login')
      .mockReturnValue(throwError(() => new Error('Connection failure')));
    
    component.submit();
    expect(component.onError).toBe(true);
  })

  it('should login and navigate', () => {
    component.form.controls['email'].setValue('yoga@studio.com');
    component.form.controls['password'].setValue('test!1234');

    const authServiceSpy = jest.spyOn(authService, 'login').mockImplementation(() => of(mockUser));
    const sessionServiceSpy = jest.spyOn(sessionService, 'logIn');
    const routerTestSpy = jest.spyOn(routerTest, 'navigate').mockImplementation(async () => true);

    component.submit();

    expect(authServiceSpy).toHaveBeenCalledWith({
      email: 'yoga@studio.com',
      password: 'test!1234',
    });
    expect(sessionServiceSpy).toBeCalledWith(mockUser);
    expect(routerTestSpy).toHaveBeenCalledWith(["/sessions"])
    expect(component.onError).toBeFalsy();
  });
});
