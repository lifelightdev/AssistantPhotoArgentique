import { ComponentFixture, TestBed } from "@angular/core/testing";

import { AccueilComponent } from "./accueil.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe("AccueilComponent", () => {
  let component: AccueilComponent;
  let fixture: ComponentFixture<AccueilComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AccueilComponent]
    });
    fixture = TestBed.createComponent(AccueilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
