import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarListElementComponent } from './car-list-element.component';

describe('CarListElementComponent', () => {
  let component: CarListElementComponent;
  let fixture: ComponentFixture<CarListElementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CarListElementComponent]
    });
    fixture = TestBed.createComponent(CarListElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
