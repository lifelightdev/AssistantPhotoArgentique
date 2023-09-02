import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Film, Marque, Modele, ModelRechercheProduit, Produit, StatutProduit, TypeProduit } from "./produit";

const optionRequete = {
  headers: new HttpHeaders({
    "Access-Control-Allow-Origin": "*"
  })
};

@Injectable({
  providedIn: "root"
})
export class ProduitService {

  // TODO à déplacer en constante globale
  private serveurUrl = `http://127.0.0.1:8181`;

  constructor(
    private http: HttpClient) {
  }

  rechercheTousLesProduits(): Observable<Produit[]> {
    return this.http.get<Produit[]>(this.serveurUrl + `/produit`, optionRequete);
  }

  rechercheDesProduits(term: ModelRechercheProduit) {
    return this.http.get<Produit[]>(`${this.serveurUrl}/produit?nom=${term.nom}&typeProduit=${term.typeProduit}&statutProduit=${term.statutProduit}&marque=${term.marque}&modele=${term.modele}&remarque=${term.remarque}`, optionRequete);
  }

  rechercheToutesLesMarques(): Observable<Marque[]> {
    return this.http.get<Marque[]>(this.serveurUrl + `/marque`, optionRequete);
  }

  rechercheTousLesModeles(): Observable<Modele[]> {
    return this.http.get<Modele[]>(this.serveurUrl + `/modele`, optionRequete);
  }

  rechercheTousLesTypesDeProduit(): Observable<TypeProduit[]> {
    return this.http.get<TypeProduit[]>(this.serveurUrl + `/typeProduit`, optionRequete);
  }

  rechercheTousLesStatutsDeProduit(): Observable<StatutProduit[]> {
    return this.http.get<StatutProduit[]>(this.serveurUrl + `/statutProduit`, optionRequete);
  }

  getProduit(id: number) {
    return this.http.get<Produit>(this.serveurUrl + "/produit/" + id, optionRequete);
  }

  getFilm(id: number) {
    return this.http.get<Film>(this.serveurUrl + "/film/" + id, optionRequete);
  }

  rechercheTousLesFilmsDisponible() {
    return this.http.get<Film[]>(this.serveurUrl + `/film?statutFilm=1`, optionRequete);
  }

  rechercheTousLesProduitsDisponible() {
    return this.http.get<Produit[]>(this.serveurUrl + `/produit?statutProduit=1`, optionRequete);
  }
}
