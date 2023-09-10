import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailMaterielComponent } from './detail.materiel.component';
import { Materiel } from "../materiel";

describe('MaterielComponent', () => {
  let component: DetailMaterielComponent;
  let fixture: ComponentFixture<DetailMaterielComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailMaterielComponent]
    });
    fixture = TestBed.createComponent(DetailMaterielComponent);
    component = fixture.componentInstance;
    component.materiel = new Materiel();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
