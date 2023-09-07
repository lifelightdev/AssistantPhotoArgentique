import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaterielObjectifComponent } from './materiel.objectif.component';

describe('MaterielObjectifComponent', () => {
  let component: MaterielObjectifComponent;
  let fixture: ComponentFixture<MaterielObjectifComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MaterielObjectifComponent]
    });
    fixture = TestBed.createComponent(MaterielObjectifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
