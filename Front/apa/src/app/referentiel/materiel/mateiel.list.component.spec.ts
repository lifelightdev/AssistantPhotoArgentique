import {TestBed} from '@angular/core/testing';
import {MaterielListComponent} from './materiel.list.component';
import {AppComponent} from "../../app.component";

describe('MaterielListComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
    declarations: [MaterielListComponent]
  }));

  it('should create', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
