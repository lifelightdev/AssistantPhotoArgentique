import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReferentielListComponent } from './referentiel.list.component';

describe('ReferentielListComponent', () => {
  let component: ReferentielListComponent;
  let fixture: ComponentFixture<ReferentielListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReferentielListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReferentielListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
