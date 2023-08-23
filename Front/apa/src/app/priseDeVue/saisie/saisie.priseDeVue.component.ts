import { Component } from "@angular/core";
import { PriseDeVueService } from "../priseDeVue.service";
import { Position, PriseDeVue, StatutPriseDeVue } from "../priseDeVue";
import { Materiel } from "../../referentiel/materiel/materiel";
import { MaterielService } from "../../referentiel/materiel/materiel.service";
import { ProduitService } from "../../referentiel/produit/produit.service";
import { Film } from "../../referentiel/produit/produit";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: "app-saisie.prise-de-vue",
  templateUrl: "./saisie.priseDeVue.component.html",
  styleUrls: ["./saisie.priseDeVue.component.css"]
})
export class SaisiePriseDeVueComponent {

  constructor(
    private route: ActivatedRoute,
    private priseDeVueService: PriseDeVueService,
    private materielService: MaterielService,
    private produitService: ProduitService) {
  }

  priseDeVue: PriseDeVue = new PriseDeVue();
  positions: Position[] = [];
  statuts: StatutPriseDeVue[] = [];
  materiels: Materiel[] = [];
  films: Film[] = [];
  errorMessage: String | undefined;
  selectedStatus: number | undefined;
  selectedPosition: number | undefined;
  datePriseDeVue: Date | undefined;
  heurePriseDeVue: String | undefined;

  submitted = false;

  async ngOnInit() {
    const id = parseInt(this.route.snapshot.paramMap.get("id")!, 10);
    if (isNaN(id)) {
      this.materielService.rechercheTousLesMaterielsDisponible().subscribe(data => {
        this.materiels = data;
      });
      this.produitService.rechercheTousLesFilmsDisponible().subscribe(data => {
        this.films = data;
      });
      this.priseDeVueService.rechercheTousLesStatutsPrisesDeVues().subscribe(data => {
        this.statuts = data;
      });
      this.priseDeVueService.recherchePosition().subscribe(data => {
        this.positions = data;
      });
    } else {
      this.priseDeVueService.getPriseDeVue(id).subscribe(priseDeVue => {
        this.priseDeVue = priseDeVue;
        // @ts-ignore
        this.datePriseDeVue = this.priseDeVue.date;
        // @ts-ignore
        const [hours, minutes] = (new Date(this.priseDeVue.date).toTimeString()).split(":");
        // @ts-ignore
        this.heurePriseDeVue = hours + ":" + minutes;
        this.selectedStatus = this.priseDeVue.statutPriseDeVue?.id;
        this.priseDeVueService.rechercheTousLesStatutsPrisesDeVues().subscribe(data => {
          this.statuts = data;
        });
        this.selectedPosition = this.priseDeVue.position?.id;
        this.priseDeVueService.recherchePosition().subscribe(data => {
          this.positions = data;
        });
        this.priseDeVueService.rechercheTousLesMaterielsDisponible(id).subscribe(data => {
          this.materiels = data;
        });
        this.priseDeVueService.rechercheTousLesFilmsDisponible(id).subscribe(data => {
          this.films = data;
        });
      });
    }
  }

  onSubmit() {
    this.submitted = true;
    this.errorMessage = "";
    // @ts-ignore
    this.priseDeVue.statutPriseDeVue = this.statuts[this.selectedStatus - 1];
    // @ts-ignore
    this.priseDeVue.position = this.positions[this.selectedPosition - 1];
    if (this.datePriseDeVue != undefined || this.heurePriseDeVue != undefined) {
      // @ts-ignore
      const [day, month, year] = new Date(this.datePriseDeVue).toLocaleDateString().split("/");
      // @ts-ignore
      const [hour, minute] = this.heurePriseDeVue.split(":");
      // @ts-ignore
      this.priseDeVue.date = new Date(Date.UTC(year, month - 1, day, hour, minute, 0));
    }
    this.priseDeVueService.enregistreUnePriseDeVue(this.priseDeVue).subscribe(
      () => {
        location.reload();
      },
      (error) => {
        console.log(error);
        this.errorMessage = error;
      }
    );
  }
}
