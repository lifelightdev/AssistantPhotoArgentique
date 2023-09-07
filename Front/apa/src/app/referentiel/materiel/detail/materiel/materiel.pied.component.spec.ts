import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaterielPiedComponent } from './materiel.pied.component';

describe('MaterielPiedComponent', () => {
  let component: MaterielPiedComponent;
  let fixture: ComponentFixture<MaterielPiedComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MaterielPiedComponent]
    });
    fixture = TestBed.createComponent(MaterielPiedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
