import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Film, Marque, Modele, ModelRechercheProduit, Produit, StatutProduit, TypeProduit } from "./produit";
import { AppSettings } from "../../AppSettings";

const optionRequete = {
  headers: new HttpHeaders({
    "Access-Control-Allow-Origin": "*"
  })
};

@Injectable({
  providedIn: "root"
})
export class ProduitService {

  constructor(
    private http: HttpClient) {
  }

  rechercheTousLesProduits(): Observable<Produit[]> {
    return this.http.get<Produit[]>(AppSettings.API_ENDPOINT + `/produit`, optionRequete);
  }

  rechercheDesProduits(term: ModelRechercheProduit) {
    return this.http.get<Produit[]>(`${AppSettings.API_ENDPOINT}/produit?nom=${term.nom}&typeProduit=${term.typeProduit}&statutProduit=${term.statutProduit}&marque=${term.marque}&modele=${term.modele}&remarque=${term.remarque}`, optionRequete);
  }

  rechercheToutesLesMarques(): Observable<Marque[]> {
    return this.http.get<Marque[]>(AppSettings.API_ENDPOINT + `/marque`, optionRequete);
  }

  rechercheTousLesModeles(): Observable<Modele[]> {
    return this.http.get<Modele[]>(AppSettings.API_ENDPOINT + `/modele`, optionRequete);
  }

  rechercheTousLesTypesDeProduit(): Observable<TypeProduit[]> {
    return this.http.get<TypeProduit[]>(AppSettings.API_ENDPOINT + `/typeProduit`, optionRequete);
  }

  rechercheTousLesStatutsDeProduit(): Observable<StatutProduit[]> {
    return this.http.get<StatutProduit[]>(AppSettings.API_ENDPOINT + `/statutProduit`, optionRequete);
  }

  getProduit(id: number) {
    return this.http.get<Produit>(AppSettings.API_ENDPOINT + "/produit/" + id, optionRequete);
  }

  getFilm(id: number) {
    return this.http.get<Film>(AppSettings.API_ENDPOINT + "/film/" + id, optionRequete);
  }

  rechercheTousLesFilmsDisponible() {
    return this.http.get<Film[]>(AppSettings.API_ENDPOINT + `/film?statutFilm=1`, optionRequete);
  }

  rechercheTousLesProduitsDisponible() {
    return this.http.get<Produit[]>(AppSettings.API_ENDPOINT + `/produit?statutProduit=1`, optionRequete);
  }
}
