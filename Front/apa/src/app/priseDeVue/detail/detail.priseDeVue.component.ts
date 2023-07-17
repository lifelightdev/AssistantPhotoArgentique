import {Component, Input} from '@angular/core';
import {PriseDeVue} from "../priseDeVue";

@Component({
  selector: 'app-detail-priseDeVue',
  templateUrl: './detail.priseDeVue.component.html',
  styleUrls: ['./detail.priseDeVue.component.css']
})
export class DetailPriseDeVueComponent {
  @Input() priseDeVue!: PriseDeVue;
}