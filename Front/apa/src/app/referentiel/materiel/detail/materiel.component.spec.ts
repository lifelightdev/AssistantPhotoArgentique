import { ComponentFixture, TestBed } from "@angular/core/testing";

import { MaterielComponent } from "./materiel.component";
import { RouterModule } from "@angular/router";
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe("DetailComponent", () => {
  let component: MaterielComponent;
  let fixture: ComponentFixture<MaterielComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterModule.forRoot([]),
        HttpClientTestingModule],
      declarations: [MaterielComponent]
    });
    fixture = TestBed.createComponent(MaterielComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
