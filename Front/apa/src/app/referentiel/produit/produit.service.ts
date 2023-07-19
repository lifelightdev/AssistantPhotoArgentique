import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Marque, Modele, ModelRechercheProduit, Produit, StatutProduit, TypeProduit} from "./produit";

const optionRequete = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin': '*'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ProduitService {

  private produitUrl = `http://localhost:8081/produit`;
  private marqueUrl = `http://localhost:8081/marque`;
  private modeleUrl = `http://localhost:8081/modele`;
  private typeUrl = `http://localhost:8081/typeProduit`;
  private statutUrl = `http://localhost:8081/statutProduit`;

  constructor(
    private http: HttpClient) {
  }

  findAllProduit(): Observable<Produit[]> {
    return this.http.get<Produit[]>(this.produitUrl, optionRequete);
  }

  searchProduits(term: ModelRechercheProduit) {
    return this.http.get<Produit[]>(`${this.produitUrl}?nom=${term.nom}&typeProduit=${term.typeProduit}&statutProduit=${term.statutProduit}&marque=${term.marque}&modele=${term.modele}&remarque=${term.remarque}`, optionRequete)
  }

  findAllMarque(): Observable<Marque[]> {
    return this.http.get<Marque[]>(this.marqueUrl, optionRequete);
  }

  findAllModele(): Observable<Modele[]> {
    return this.http.get<Modele[]>(this.modeleUrl, optionRequete);
  }
  findAllType(): Observable<TypeProduit[]> {
    return this.http.get<TypeProduit[]>(this.typeUrl, optionRequete);
  }

  findAllStatutProduit(): Observable<StatutProduit[]> {
    return this.http.get<StatutProduit[]>(this.statutUrl, optionRequete);
  }

}
