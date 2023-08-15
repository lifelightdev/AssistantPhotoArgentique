import { TestBed } from "@angular/core/testing";
import { MaterielListComponent } from "./materiel.list.component";
import { AppComponent } from "../../app.component";
import { TranslateModule } from "@ngx-translate/core";

describe("MaterielListComponent", () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [ TranslateModule.forRoot() ],
    declarations: [ MaterielListComponent ]
  }));

  it("should create", () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
