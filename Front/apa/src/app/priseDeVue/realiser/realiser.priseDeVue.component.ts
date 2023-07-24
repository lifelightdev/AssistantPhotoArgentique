import {Component} from '@angular/core';
import {PositionSoleil, PriseDeVue} from "../priseDeVue";
import {ActivatedRoute} from "@angular/router";
import {PriseDeVueService} from "../priseDeVue.service";

@Component({
  selector: 'app-realiser-priseDeVue',
  templateUrl: './realiser.priseDeVue.component.html',
  styleUrls: ['./realiser.priseDeVue.component.css']
})
export class RealiserPriseDeVueComponent {
  priseDeVue: PriseDeVue | undefined;
  positionSoleil: PositionSoleil | undefined;

  constructor(
    private route: ActivatedRoute,
    private priseDeVueService: PriseDeVueService
  ) { }

  ngOnInit() {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    const latitude = parseFloat(this.route.snapshot.paramMap.get('latitude')!);
    const longitude = parseFloat(this.route.snapshot.paramMap.get('latitude')!);
    const date = this.route.snapshot.paramMap.get('date')!;
    this.priseDeVueService.getPriseDeVue(id).subscribe(priseDeVue => this.priseDeVue = priseDeVue);
    this.priseDeVueService.recherchePositionSoleil(id, latitude, longitude, date).subscribe(data => { this.positionSoleil = data; });
  }
}
