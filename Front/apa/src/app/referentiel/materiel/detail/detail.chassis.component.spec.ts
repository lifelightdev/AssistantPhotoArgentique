import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailChassisComponent } from './detail.chassis.component';
import { Chassis } from "../materiel";

describe('MaterielChassisComponent', () => {
  let component: DetailChassisComponent;
  let fixture: ComponentFixture<DetailChassisComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailChassisComponent]
    });
    fixture = TestBed.createComponent(DetailChassisComponent);
    component = fixture.componentInstance;
    component.chassis = new Chassis();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
