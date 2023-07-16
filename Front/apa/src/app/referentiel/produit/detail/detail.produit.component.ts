import {Component, Input} from '@angular/core';
import {Produit} from "../produit";

@Component({
  selector: 'app-detail-produit',
  templateUrl: './detail.produit.component.html',
  styleUrls: ['./detail.produit.component.css']
})
export class DetailProduitComponent {
  @Input() produit!: Produit;
}
