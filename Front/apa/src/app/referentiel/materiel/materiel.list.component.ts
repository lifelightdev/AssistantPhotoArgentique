import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Materiel, ModelRechercheMateriel} from "./materiel";
import {MaterielService} from "./materiel.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {Marque, Modele, TypeMateriel, SousType, StatutMateriel} from "./materiel";

@Component({
  selector: 'app-materiel.list',
  templateUrl: './materiel.list.component.html',
  styleUrls: ['./materiel.list.component.css']
})
export class MaterielListComponent implements OnInit, AfterViewInit {

  constructor(private materielService: MaterielService) { }

  materiel = new Materiel();
  modelRecherche: ModelRechercheMateriel = new ModelRechercheMateriel();
  marques: Marque[] = [];
  modeles: Modele[] = [];
  types: TypeMateriel[] = [];
  sousTypes: SousType[] = [];
  statuts: StatutMateriel[] = [];

  ngOnInit(): void {
    this.materielService.rechercheTousLesMateriel().subscribe(data => { this.dataSource.data = data; });
    this.materielService.rechercheToutesLesMarques().subscribe(data => { this.marques = data; });
    this.materielService.rechercheTousLesModeles().subscribe(data => { this.modeles = data; });
    this.materielService.rechercheTousLesTypesDeMateriel().subscribe(data => { this.types = data; });
    this.materielService.rechercheTousLesSousType().subscribe(data => { this.sousTypes = data; });
    this.materielService.rechercheTousLesStatutsDeMateriel().subscribe(data => { this.statuts = data; });
  }

  submitted = false;

  onSubmit() {
    this.submitted = true;
    this.materielService.recherchedesMateriels(this.modelRecherche).subscribe(data => { this.dataSource.data = data; });
  }

  displayedColumns = ["ID", "Nom", "Type", "Sous type", "Statut", "Marque", "Modéle", "Photo", "Mode d'emploie", "Remarque", "Tâche"];
  dataSource = new MatTableDataSource<Materiel>();

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  onSelect(materiel: Materiel): void {
    this.materiel = materiel;
  }
}
