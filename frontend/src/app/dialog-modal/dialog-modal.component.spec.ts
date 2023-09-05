import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogModalComponent } from './dialog-modal.component';

describe('DialogModalComponent', () => {
  let component: DialogModalComponent;
  let fixture: ComponentFixture<DialogModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DialogModalComponent]
    });
    fixture = TestBed.createComponent(DialogModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
