import {Component} from '@angular/core';
import {PriseDeVue} from "../priseDeVue";
import {ActivatedRoute} from "@angular/router";
import {PriseDeVueService} from "../priseDeVue.service";

@Component({
  selector: 'app-realiser-priseDeVue',
  templateUrl: './realiser.priseDeVue.component.html',
  styleUrls: ['./realiser.priseDeVue.component.css']
})
export class RealiserPriseDeVueComponent {
  priseDeVue: PriseDeVue | undefined;

  constructor(
    private route: ActivatedRoute,
    private  priseDeVueService:  PriseDeVueService
  ) {}

  ngOnInit(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.priseDeVueService.getPriseDeVue(id).subscribe(priseDeVue => this.priseDeVue = priseDeVue)
  }
}
