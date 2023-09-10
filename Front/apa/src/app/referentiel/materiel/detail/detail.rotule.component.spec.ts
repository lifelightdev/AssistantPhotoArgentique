import { ComponentFixture, TestBed } from "@angular/core/testing";

import { DetailRotuleComponent } from "./detail.rotule.component";
import { Rotule } from "../materiel";
import { TranslateModule } from "@ngx-translate/core";

describe("MaterielRotuleComponent", () => {
  let component: DetailRotuleComponent;
  let fixture: ComponentFixture<DetailRotuleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        TranslateModule.forRoot()],
      declarations: [DetailRotuleComponent]
    });
    fixture = TestBed.createComponent(DetailRotuleComponent);
    component = fixture.componentInstance;
    component.rotule = new Rotule();
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
