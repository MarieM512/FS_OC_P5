import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { ListComponent } from './list.component';
import { SessionApiService } from '../../services/session-api.service';
import { of } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';

describe('ListComponent', () => {
  let component: ListComponent;
  let fixture: ComponentFixture<ListComponent>;

  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  }

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

  const mockSessionApiService = {
    all: jest.fn().mockReturnValue(of(mockSessions))
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListComponent],
      imports: [HttpClientModule, MatCardModule, MatIconModule, RouterTestingModule],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionApiService }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should display list of sessions', () => {
    expect(component.sessions$).toBeTruthy();
    component.sessions$.subscribe((sessions) => {
      expect(sessions).toHaveLength(2);
    });

    const compiled = fixture.nativeElement;
    const cards = compiled.querySelectorAll('mat-card.item');
    expect(cards).toHaveLength(2);
    expect(cards[0].querySelector('mat-card-title').textContent).toContain(mockSessions[0].name);
  });
});
