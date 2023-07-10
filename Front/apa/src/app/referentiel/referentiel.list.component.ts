import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Materiel} from "./materiel";
import {MaterielService} from "./materiel.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MarqueService} from "./marque.service";
import {Marque, Modele, TypeMateriel, SousType} from "./materiel";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ModeleService} from "./modele.service";
import {TypeMaterielService} from "./typeMateriel.service";

@Component({
  selector: 'app-referentiel.list',
  templateUrl: './referentiel.list.component.html',
  styleUrls: ['./referentiel.list.component.css']
})
export class ReferentielListComponent implements OnInit, AfterViewInit {

  options: FormGroup;

  constructor(
    private materielService: MaterielService,
    private marqueService: MarqueService,
    private modeleService: ModeleService,
    private typeMaterielService: TypeMaterielService,
    fb: FormBuilder) {
    this.options = fb.group({
      hideRequired: false,
      floatLabel: 'auto',
      name: ['', Validators.required]
    });
  }

  marques: Marque[] = [];
  modeles: Modele[] = [];
  types: TypeMateriel[] = [];
  sousTypes: SousType[] = [];
  ngOnInit(): void {
    this.materielService.findAllMateriel().subscribe(data => {
      this.dataSource.data = data;
    });
    this.marqueService.findAll().subscribe(data => {
      this.marques = data;
    });
    this.modeleService.findAll().subscribe(data => {
      this.modeles = data;
    });
    this.typeMaterielService.findAll().subscribe(data => {
      this.types = data;
    });
    this.materielService.findAllSousTypeMateriel().subscribe(data => {
      this.sousTypes = data;
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

  materiel= new Materiel ();

  model = this.materiel;

  submitted = false;

  onSubmit() { this.submitted = true;
    console.log(this.options.getRawValue());
    console.log(this.model);
  }

}
