import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailObjectifComponent } from './detail.objectif.component';
import { Objectif } from "../materiel";

describe('MaterielObjectifComponent', () => {
  let component: DetailObjectifComponent;
  let fixture: ComponentFixture<DetailObjectifComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailObjectifComponent]
    });
    fixture = TestBed.createComponent(DetailObjectifComponent);
    component = fixture.componentInstance;
    component.objectif = new Objectif();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
