import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ModelVue, PositionSoleil, PriseDeVue, Vue} from "../priseDeVue";
import {ActivatedRoute} from "@angular/router";
import {PriseDeVueService} from "../priseDeVue.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {AppareilPhoto} from "../../referentiel/materiel/materiel";
import {Film} from "../../referentiel/produit/produit";

@Component({
  selector: 'app-detail-priseDeVue',
  templateUrl: './detail.priseDeVue.component.html',
  styleUrls: ['./detail.priseDeVue.component.css']
})
export class DetailPriseDeVueComponent implements OnInit, AfterViewInit {
  priseDeVue: PriseDeVue | undefined;
  positionSoleil: PositionSoleil | undefined;
  modelVue: ModelVue = new ModelVue();
  appareilsPhoto: AppareilPhoto [] = [];
  films: Film[] = [];
  submitted = false;
  errorMessage: String | undefined;

  constructor(
    private route: ActivatedRoute,
    private priseDeVueService: PriseDeVueService
  ) { }

  ngOnInit(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    const latitude = parseFloat(this.route.snapshot.paramMap.get('latitude')!);
    const longitude = parseFloat(this.route.snapshot.paramMap.get('latitude')!);
    const date = this.route.snapshot.paramMap.get('date')!;
    this.priseDeVueService.getPriseDeVue(id).subscribe(priseDeVue => this.priseDeVue = priseDeVue);
    this.priseDeVueService.recherchePositionSoleil(id, latitude, longitude, date).subscribe(data => {
      this.positionSoleil = data;
    });
    this.priseDeVueService.rechercheTousLesVues(id).subscribe(data => {
      this.dataSource.data = data;
    });
    this.priseDeVueService.rechercheTousLesAppareilsPhotoDUnuePriseDeVue(id).subscribe(data => {
      this.appareilsPhoto = data;
    });
    this.priseDeVueService.rechercheTousLesFilmsDUnuePriseDeVue(id).subscribe(data => {
      this.films = data;
    });
  }

  displayedColumns = ["Nom", "Statut", "Sensibilite", "Ouverture", "Vitesse"];
  dataSource = new MatTableDataSource<Vue>();

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  onSelect(vue: Vue): void {
    console.log("Vue" + vue.nom);
  }

  protected readonly String = String;

  ajouter() {
    this.errorMessage = "";
    this.submitted = true;
    if (this.priseDeVue?.id) {
      this.modelVue.id = this.priseDeVue?.id;
    }

    this.priseDeVueService.ajouterVue(this.modelVue).subscribe(
      (response) => { location.reload(); },
      (error) => {
        this.errorMessage = error;
      }
    );

    this.priseDeVueService.rechercheTousLesVues(this.modelVue.id).subscribe(data => {
      this.dataSource.data = data;
    });
  }
}
