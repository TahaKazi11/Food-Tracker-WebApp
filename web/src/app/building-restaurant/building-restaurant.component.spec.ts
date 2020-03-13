import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BuildingRestaurantComponent } from './building-restaurant.component';

describe('BuildingRestaurantComponent', () => {
  let component: BuildingRestaurantComponent;
  let fixture: ComponentFixture<BuildingRestaurantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BuildingRestaurantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BuildingRestaurantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
