import { ComponentFixture, TestBed } from "@angular/core/testing";
import { ProduitListComponent } from "./produit.list.component";
import { TranslateModule } from "@ngx-translate/core";
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe("ProduitListComponent", () => {
  let component: ProduitListComponent;
  let fixture: ComponentFixture<ProduitListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        TranslateModule.forRoot(),
        HttpClientTestingModule],
      declarations: [ ProduitListComponent ]
    });
    fixture = TestBed.createComponent(ProduitListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
