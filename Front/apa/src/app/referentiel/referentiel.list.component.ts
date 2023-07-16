import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {AppareilPhoto, Materiel, ModelRecherche} from "./materiel";
import {MaterielService} from "./materiel.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {Marque, Modele, TypeMateriel, SousType, StatutMateriel} from "./materiel";

@Component({
  selector: 'app-referentiel.list',
  templateUrl: './referentiel.list.component.html',
  styleUrls: ['./referentiel.list.component.css']
})
export class ReferentielListComponent implements OnInit, AfterViewInit {

  constructor(
    private materielService: MaterielService) { }

  materiel = new Materiel();
  modelRecherche: ModelRecherche = new ModelRecherche();
  marques: Marque[] = [];
  modeles: Modele[] = [];
  types: TypeMateriel[] = [];
  sousTypes: SousType[] = [];
  statuts: StatutMateriel[] = [];

  ngOnInit(): void {
    this.materielService.findAllMateriel().subscribe(data => { this.dataSource.data = data; });
    this.materielService.findAllMarque().subscribe(data => { this.marques = data; });
    this.materielService.findAllModele().subscribe(data => { this.modeles = data; });
    this.materielService.findAllType().subscribe(data => { this.types = data; });
    this.materielService.findAllSousType().subscribe(data => { this.sousTypes = data; });
    this.materielService.findAllStatut().subscribe(data => { this.statuts = data; });
  }

  submitted = false;

  onSubmit() {
    this.submitted = true;
    console.log(this.modelRecherche);
    this.materielService.searchMateriels(this.modelRecherche).subscribe(data => { this.dataSource.data = data; });
  }

  displayedColumns = ["Identifiant", "Nom", "Type", "Sous type", "Statut", "Marque", "Modéle", "Photo", "Mode d'emploie", "Remarque"];
  dataSource = new MatTableDataSource<Materiel>();

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  appareilPhoto = new AppareilPhoto();

  onSelect(materiel: Materiel): void {
    this.materiel = materiel;
    console.log('matériel ' + this.materiel.nom);
    console.log('Id du matériel de l appareil photo ' + this.materiel.id);
    this.materielService.findAppareilPhoto(this.materiel.id).subscribe(data => {
      this.appareilPhoto = data;
    });
    console.log('type de l appareil photo ' + this.appareilPhoto.typeAppareilPhoto?.nom);
  }
}
