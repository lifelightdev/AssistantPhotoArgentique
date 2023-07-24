import {Component, OnInit} from '@angular/core';
import {PriseDeVueService} from "../priseDeVue.service";
import {Vue} from "../priseDeVue";
import {ActivatedRoute} from "@angular/router";
import {Vitesse, Ouverture} from "../../referentiel/materiel/materiel";

@Component({
  selector: 'app-ajouter-vue',
  templateUrl: './ajouter-vue.component.html',
  styleUrls: ['./ajouter-vue.component.css']
})
export class AjouterVueComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private priseDeVueService: PriseDeVueService) {
  }

  vue = new Vue();
  ouvertures: Ouverture[] = [];
  vitesses: Vitesse[] = [];

  ngOnInit(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.priseDeVueService.getVue(id).subscribe(vue => {
      this.vue = vue;
    });
    this.priseDeVueService.rechercheToutesLesOuvertures(id).subscribe(ouverture => {
      this.ouvertures = ouverture;
    });
    this.priseDeVueService.rechercheToutesLesVitesses(id).subscribe(vitesse => {
      this.vitesses = vitesse;
    });
  }

}
