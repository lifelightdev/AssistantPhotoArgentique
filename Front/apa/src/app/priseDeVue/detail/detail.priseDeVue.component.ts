import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {PositionSoleil, PriseDeVue, Vue} from "../priseDeVue";
import {ActivatedRoute} from "@angular/router";
import {PriseDeVueService} from "../priseDeVue.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-detail-priseDeVue',
  templateUrl: './detail.priseDeVue.component.html',
  styleUrls: ['./detail.priseDeVue.component.css']
})
export class DetailPriseDeVueComponent implements OnInit, AfterViewInit {
  priseDeVue: PriseDeVue | undefined;
  positionSoleil: PositionSoleil | undefined;

  constructor(
    private route: ActivatedRoute,
    private priseDeVueService: PriseDeVueService
  ) {
  }

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
  }

  displayedColumns = ["AppareilPhoto", "Film", "Sensibilite", "Ouverture", "Vitesse", "Photo"];
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
    let nouvelleVue = new Vue();
    nouvelleVue.nom = this.priseDeVue?.nom;
    // @ts-ignore
    nouvelleVue.appareilPhoto = this.priseDeVue?.vues?.at(0).appareilPhoto;
    // @ts-ignore
    nouvelleVue.film = this.priseDeVue?.vues?.at(0).film;
    this.priseDeVueService.ajouterVue(nouvelleVue);
  }
}
