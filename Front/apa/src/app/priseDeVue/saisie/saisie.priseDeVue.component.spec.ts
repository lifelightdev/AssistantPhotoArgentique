import { ComponentFixture, TestBed } from "@angular/core/testing";

import { SaisiePriseDeVueComponent } from "./saisie.priseDeVue.component";
import { RouterModule } from "@angular/router";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { MatInputModule } from "@angular/material/input";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatTableModule } from "@angular/material/table";
import { MatSelectModule } from "@angular/material/select";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatNativeDateModule } from "@angular/material/core";

describe("SaisiePriseDeVueComponent", () => {
  let component: SaisiePriseDeVueComponent;
  let fixture: ComponentFixture<SaisiePriseDeVueComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterModule.forRoot([]),
        HttpClientTestingModule,
        BrowserAnimationsModule,
        MatInputModule,
        MatPaginatorModule,
        MatTableModule,
        MatSelectModule,
        MatNativeDateModule,
        MatDatepickerModule,
        FormsModule,
        ReactiveFormsModule],
      declarations: [SaisiePriseDeVueComponent]
    });
    fixture = TestBed.createComponent(SaisiePriseDeVueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
