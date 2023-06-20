import { TestBed } from '@angular/core/testing';
import { ReferentielListComponent } from './referentiel.list.component';
import { AppComponent } from "../app.component";

describe('ReferentielListComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
      declarations: [ ReferentielListComponent ]
  }));

  it('should create', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
