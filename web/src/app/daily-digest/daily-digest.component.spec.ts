import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DailyDigestComponent } from './daily-digest.component';

describe('DailyDigestComponent', () => {
  let component: DailyDigestComponent;
  let fixture: ComponentFixture<DailyDigestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DailyDigestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DailyDigestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
