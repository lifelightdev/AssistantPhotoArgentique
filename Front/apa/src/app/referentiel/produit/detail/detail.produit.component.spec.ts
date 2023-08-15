import { ComponentFixture, TestBed } from "@angular/core/testing";
import { DetailProduitComponent } from "./detail.produit.component";
import { RouterModule } from "@angular/router";
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe("DetailComponent", () => {
  let component: DetailProduitComponent;
  let fixture: ComponentFixture<DetailProduitComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterModule.forRoot([]),
        HttpClientTestingModule
      ],
      declarations: [DetailProduitComponent]
    });
    fixture = TestBed.createComponent(DetailProduitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
