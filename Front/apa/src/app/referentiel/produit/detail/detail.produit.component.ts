import {Component, OnInit} from '@angular/core';
import {Film, Produit} from "../produit";
import {ActivatedRoute} from "@angular/router";
import {ProduitService} from "../produit.service";

@Component({
  selector: 'app-detail-produit',
  templateUrl: './detail.produit.component.html',
  styleUrls: ['./detail.produit.component.css']
})
export class DetailProduitComponent implements OnInit {

  produit: Produit | undefined;
  film: Film | undefined;

  constructor(
    private route: ActivatedRoute,
    private produitService: ProduitService
  ) {
  }

  ngOnInit(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.produitService.getProduit(id).subscribe(produit => this.produit = produit);
    this.produitService.getFilm(id).subscribe(film => this.film = film);
  }

}
