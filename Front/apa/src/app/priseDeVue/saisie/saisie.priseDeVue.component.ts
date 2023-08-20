import { Component } from '@angular/core';
import { PriseDeVueService } from "../priseDeVue.service";
import { Position, PriseDeVue, StatutPriseDeVue } from "../priseDeVue";
import { Materiel } from "../../referentiel/materiel/materiel";
import { MaterielService } from "../../referentiel/materiel/materiel.service";
import { ProduitService } from "../../referentiel/produit/produit.service";
import { Film } from "../../referentiel/produit/produit";

@Component({
  selector: 'app-saisie.prise-de-vue',
  templateUrl: './saisie.priseDeVue.component.html',
  styleUrls: ['./saisie.priseDeVue.component.css']
})
export class SaisiePriseDeVueComponent {

  constructor(
    private priseDeVueService: PriseDeVueService,
    private materielService: MaterielService,
    private produitService: ProduitService) {
  }

  priseDeVue: PriseDeVue = new PriseDeVue();
  positions: Position[] = [];
  statuts: StatutPriseDeVue[] = [];
  materiels: Materiel[] = [];
  films: Film[] = [];

  submitted = false;

  ngOnInit(): void {
    this.priseDeVueService.recherchePosition().subscribe(data => { this.positions = data; });
    this.priseDeVueService.rechercheTousLesStatutPriseDeVue().subscribe(data => { this.statuts = data; });
    this.materielService.rechercheTousLesMaterielsDisponible().subscribe(data => { this.materiels = data; });
    this.produitService.rechercheTousLesFilmsDisponible().subscribe(data => { this.films = data; });
  }

  onSubmit() {
    this.submitted = true;
    console.log("prise de vue " + this.priseDeVue.materiels?.length)
    this.priseDeVueService.enregistreUnePriseDeVue(this.priseDeVue).subscribe();
  }
}
