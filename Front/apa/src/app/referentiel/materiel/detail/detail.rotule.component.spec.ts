import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailRotuleComponent } from './detail.rotule.component';

describe('MaterielRotuleComponent', () => {
  let component: DetailRotuleComponent;
  let fixture: ComponentFixture<DetailRotuleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailRotuleComponent]
    });
    fixture = TestBed.createComponent(DetailRotuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
