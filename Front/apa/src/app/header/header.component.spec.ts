import { TestBed } from '@angular/core/testing';
import { HeaderComponent } from './header.component';
import { AppComponent } from "../app.component";
import { TranslateModule } from "@ngx-translate/core";

describe('HeaderComponent', () => {

  beforeEach(() => TestBed.configureTestingModule({
    imports: [ TranslateModule.forRoot() ],
      declarations: [ HeaderComponent ]
  }));

  it('should create', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'apa'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('apa');
  });

});
