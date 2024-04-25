import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  let authService: AuthService;
  let routerTest: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,  
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    authService = TestBed.inject(AuthService);
    routerTest = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display error when empty field', () => {
    const authServiceSpy = jest.spyOn(authService, 'register').mockReturnValue(throwError(() => new Error('Connection failure')));
    
    component.submit();

    expect(authServiceSpy).toBeCalled()
    expect(component.onError).toBeTruthy();
  })

  it('should register and navigate', () => {
    const authServiceSpy = jest.spyOn(authService, 'register').mockImplementation(() => of(undefined));
    const routerTestSpy = jest.spyOn(routerTest, 'navigate').mockImplementation(async () => true);

    component.submit();

    expect(authServiceSpy).toBeCalled();
    expect(routerTestSpy).toHaveBeenCalledWith(["/login"])
    expect(component.onError).toBeFalsy();
  })
});
