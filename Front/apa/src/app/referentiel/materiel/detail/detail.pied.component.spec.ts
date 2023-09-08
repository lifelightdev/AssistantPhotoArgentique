import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailPiedComponent } from './detail.pied.component';

describe('MaterielPiedComponent', () => {
  let component: DetailPiedComponent;
  let fixture: ComponentFixture<DetailPiedComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailPiedComponent]
    });
    fixture = TestBed.createComponent(DetailPiedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
