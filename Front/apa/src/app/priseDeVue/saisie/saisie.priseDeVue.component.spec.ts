import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaisiePriseDeVueComponent } from './saisie.priseDeVue.component';

describe('SaisiePriseDeVueComponent', () => {
  let component: SaisiePriseDeVueComponent;
  let fixture: ComponentFixture<SaisiePriseDeVueComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SaisiePriseDeVueComponent]
    });
    fixture = TestBed.createComponent(SaisiePriseDeVueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
