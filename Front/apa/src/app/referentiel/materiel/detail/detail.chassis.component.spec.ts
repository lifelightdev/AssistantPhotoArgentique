import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailChassisComponent } from './detail.chassis.component';

describe('MaterielChassisComponent', () => {
  let component: DetailChassisComponent;
  let fixture: ComponentFixture<DetailChassisComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailChassisComponent]
    });
    fixture = TestBed.createComponent(DetailChassisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
