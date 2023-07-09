import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Materiel} from "./materiel";
import {MaterielService} from "./materiel.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MarqueService} from "./marque.service";
import {Marque} from "./materiel";

@Component({
  selector: 'app-referentiel.list',
  templateUrl: './referentiel.list.component.html',
  styleUrls: ['./referentiel.list.component.css']
})
export class ReferentielListComponent implements OnInit, AfterViewInit {

  constructor(
    private materielService: MaterielService,
    private marqueService: MarqueService) { }

  materiels: Materiel[] = [];
  marques: Marque[] = [];
  ngOnInit(): void {
    this.materielService.findAll().subscribe(data => {
      this.dataSource.data = data;
    });
    this.marqueService.findAll().subscribe(data => {
      this.marques = data;
    });
  }

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  displayedColumns = ["Identifiant", "Nom", "Type", "Sous type", "Statut", "Marque", "Modéle", "Photo", "Mode d'emploie", "Remarque"];
  dataSource = new MatTableDataSource<Materiel>();

  onSelect(materiel: Materiel): void {
    this.materiel = materiel;
  }

  materiel: Materiel = {
    modeEmploie: undefined,
    modele: undefined,
    photo: undefined,
    remarque: undefined,
    sousType: undefined,
    statut: undefined,
    type: undefined,
    id: undefined,
    nom: undefined
  };

}
