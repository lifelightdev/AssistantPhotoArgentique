import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {
  ModelRecherchePriseDeVue,
  Position,
  PriseDeVue,
  StatutPriseDeVue
} from "./priseDeVue";
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
  modelRecherchePriseDeVue: ModelRecherchePriseDeVue = new ModelRecherchePriseDeVue();
  statuts: StatutPriseDeVue[] = [];
  positions: Position[] = [];

  ngOnInit(): void {
    this.priseDeVueService.rechercheTousLesPriseDeVue().subscribe(data => { this.dataSource.data = data; });
    this.priseDeVueService.rechercheTousLesStatutPriseDeVue().subscribe(data => { this.statuts = data; });
    this.priseDeVueService.recherchePosition().subscribe(data => { this.positions = data; });
  }

  submitted = false;

  onSubmit() {
    this.submitted = true;
    this.priseDeVueService.rechercheDesPriseDeVues(this.modelRecherchePriseDeVue).subscribe(data => { this.dataSource.data = data; });
  }

  displayedColumns = ["Tâche", "Nom", "Statut", "Date"];
  dataSource = new MatTableDataSource<PriseDeVue>();

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  onSelect(priseDeVue: PriseDeVue) {  }

}
