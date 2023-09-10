import { ComponentFixture, TestBed } from "@angular/core/testing";
import { ProduitListComponent } from "./produit.list.component";
import { TranslateModule } from "@ngx-translate/core";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { MatSelectModule } from "@angular/material/select";
import { MatPaginatorModule } from "@angular/material/paginator";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { MatInputModule } from "@angular/material/input";
import { MatTableModule } from "@angular/material/table";

describe("ProduitListComponent", () => {
  let component: ProduitListComponent;
  let fixture: ComponentFixture<ProduitListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        TranslateModule.forRoot(),
        HttpClientTestingModule,
        BrowserAnimationsModule,
        MatInputModule,
        MatPaginatorModule,
        MatTableModule,
        MatSelectModule,
        FormsModule,
        ReactiveFormsModule],
      declarations: [ProduitListComponent]
    });
    fixture = TestBed.createComponent(ProduitListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
