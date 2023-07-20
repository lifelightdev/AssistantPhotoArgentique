import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {Coordonnees, ModelRecherchePriseDeVue, PositionSoleil, PriseDeVue, StatutPriseDeVue} from "./priseDeVue";
import {PriseDeVueService} from "./priseDeVue.service";

@Component({
  selector: 'app-priseDeVue.list',
  templateUrl: './priseDeVue.list.component.html',
  styleUrls: ['./priseDeVue.list.component.css']
})
export class PriseDeVueListComponent implements OnInit, AfterViewInit {

  constructor(
    private priseDeVueService: PriseDeVueService) {
  }

  priseDeVue: PriseDeVue = new PriseDeVue();
  positionSoleil: PositionSoleil = new PositionSoleil();
  coordonnees: Coordonnees = new Coordonnees();
  modelRecherchePriseDeVue: ModelRecherchePriseDeVue = new ModelRecherchePriseDeVue();
  statuts: StatutPriseDeVue[] = [];

  ngOnInit(): void {
    this.priseDeVueService.findAllPriseDeVue().subscribe(data => { this.dataSource.data = data; });
    this.priseDeVueService.findAllStatutPriseDeVue().subscribe(data => { this.statuts = data; });
  }

  submitted = false;

  onSubmit() {
    this.submitted = true;
    this.priseDeVueService.searchPriseDeVues(this.modelRecherchePriseDeVue).subscribe(data => { this.dataSource.data = data; });
  }

  displayedColumns = ["ID", "Nom", "Statut", "Date", "Position", "Remarque"];
  dataSource = new MatTableDataSource<PriseDeVue>();

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  onSelect(priseDeVue: PriseDeVue): void {
    this.priseDeVue = priseDeVue;
    this.coordonnees.latitude = priseDeVue.latitude;
    this.coordonnees.longitude = priseDeVue.longitude;
    let date: String;
    // @ts-ignore
    date = priseDeVue.date
    this.coordonnees.date = date.substr(0,10);
    this.priseDeVueService.recherchePositionSoleil(this.coordonnees).subscribe(data => { this.positionSoleil = data; });
  }

}
