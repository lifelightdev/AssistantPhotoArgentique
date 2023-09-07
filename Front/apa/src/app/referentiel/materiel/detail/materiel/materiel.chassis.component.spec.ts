import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaterielChassisComponent } from './materiel.chassis.component';

describe('MaterielChassisComponent', () => {
  let component: MaterielChassisComponent;
  let fixture: ComponentFixture<MaterielChassisComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MaterielChassisComponent]
    });
    fixture = TestBed.createComponent(MaterielChassisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
