import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarCardComponent } from './car-card.component';

describe('CarCardComponent', () => {
  let component: CarCardComponent;
  let fixture: ComponentFixture<CarCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CarCardComponent]
    });
    fixture = TestBed.createComponent(CarCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
