import { TestBed } from '@angular/core/testing';
import { HeaderComponent } from './header.component';
import { AppComponent } from "../app.component";

describe('HeaderComponent', () => {

  beforeEach(() => TestBed.configureTestingModule({
      declarations: [ HeaderComponent ]
  }));

  it('should create', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
