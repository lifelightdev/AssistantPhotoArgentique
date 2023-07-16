import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {ProduitService} from "./produit.service";
import {ModelRechercheProduit, Produit, StatutProduit, TypeProduit} from "./produit";
import {Marque, Modele} from "../materiel/materiel";

@Component({
  selector: 'app-produit.list',
  templateUrl: './produit.list.component.html',
  styleUrls: ['./produit.list.component.css']
})
export class ProduitListComponent implements OnInit, AfterViewInit {

  constructor(
    private produitService: ProduitService) {
  }

  produit = new Produit();
  modelRechercheProduit: ModelRechercheProduit = new ModelRechercheProduit();
  marques: Marque[] = [];
  modeles: Modele[] = [];
  types: TypeProduit[] = [];
  statuts: StatutProduit[] = [];

  ngOnInit(): void {
    this.produitService.findAllProduit().subscribe(data => { this.dataSource.data = data; });
    this.produitService.findAllMarque().subscribe(data => { this.marques = data; });
    this.produitService.findAllModele().subscribe(data => { this.modeles = data; });
    this.produitService.findAllType().subscribe(data => { this.types = data; });
    this.produitService.findAllStatutProduit().subscribe(data => { this.statuts = data; });
  }

  submitted = false;

  onSubmit() {
    this.submitted = true;
    this.produitService.searchProduits(this.modelRechercheProduit).subscribe(data => { this.dataSource.data = data; });
  }

  displayedColumns = ["ID", "Nom", "Type", "Statut", "Marque", "Modéle", "Photo", "Mode d'emploie", "Remarque"];
  dataSource = new MatTableDataSource<Produit>();

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  onSelect(produit: Produit): void {
    this.produit = produit
  }

}
