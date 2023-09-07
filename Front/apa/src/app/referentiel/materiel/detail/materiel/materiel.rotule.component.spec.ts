import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaterielRotuleComponent } from './materiel.rotule.component';

describe('MaterielRotuleComponent', () => {
  let component: MaterielRotuleComponent;
  let fixture: ComponentFixture<MaterielRotuleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MaterielRotuleComponent]
    });
    fixture = TestBed.createComponent(MaterielRotuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
