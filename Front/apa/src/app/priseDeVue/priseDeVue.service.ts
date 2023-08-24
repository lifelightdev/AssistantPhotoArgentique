import { Injectable } from "@angular/core";
import { catchError, Observable, throwError } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import {
  ModelRecherchePriseDeVue,
  PositionSoleil,
  Position,
  PriseDeVue,
  StatutPriseDeVue, Vue, ModelVue
} from "./priseDeVue";
import { AppareilPhoto, Materiel, Ouverture, Vitesse } from "../referentiel/materiel/materiel";
import { Film, Produit } from "../referentiel/produit/produit";

const optionRequete = {
  headers: new HttpHeaders({
    "Access-Control-Allow-Origin": "*",
    "Content-Type": "application/json"
  })
};

@Injectable({
  providedIn: "root"
})

export class PriseDeVueService {

  private serveurUrl = `http://127.0.0.1:8081`;

  constructor(
    private http: HttpClient) {
  }

  rechercheToutesLesPrisesDeVues(): Observable<PriseDeVue[]> {
    return this.http.get<PriseDeVue[]>(`${this.serveurUrl}/priseDeVue`, optionRequete).pipe(
      catchError((err) => {
        console.error(err.error.message);
        return throwError(err.error.message);
      }));
  }

  rechercheDesPrisesDeVues(term: ModelRecherchePriseDeVue) {
    let date;
    if (term.date) {
      var jour = term.date?.getDate().toString();
      var mois = (term.date?.getMonth() + 1).toString();
      date = term.date?.getFullYear()
        + "-" + (mois.length != 2 ? "0" + mois : mois)
        + "-" + (jour.length != 2 ? "0" + jour : jour);
    }
    return this.http.get<PriseDeVue[]>(`${this.serveurUrl}/priseDeVue?nom=${term.nom}`
      + `&statutPriseDeVue=${term.statutPriseDeVue}`
      + `&position=${term.position}`
      + `&date=${date}` + `&heure=${term.heure}`
      + `&remarque=${term.remarque}`, optionRequete);
  }

  rechercheTousLesStatutsPrisesDeVues(): Observable<StatutPriseDeVue[]> {
    return this.http.get<StatutPriseDeVue[]>(`${this.serveurUrl}/statutPriseDeVue`, optionRequete).pipe(
      catchError((err) => {
        console.error(err.error.message);
        return throwError(err.error.message);
      }));
  }

  rechercheCodePostal(latitude: number, longitude: number) {
    return this.http.get<PriseDeVue[]>(`https://geo.api.gouv.fr/communes?lat=${latitude}&lon=${longitude}&fields=nom,codesPostaux&format=json&geometry=centre`);
  }

  recherchePositionSoleil(id: number, latitude: number, longitude: number, date: string) {
    return this.http.get<PositionSoleil>(`https://api.sunrise-sunset.org/json?lat=${latitude}&lng=${longitude}&date=${date}&formatted=0`);
  }

  recherchePosition() {
    return this.http.get<Position[]>(`${this.serveurUrl}/position`, optionRequete).pipe(
      catchError((err) => {
        console.error(err.error.message);
        return throwError(err.error.message);
      }));
  }

  getPriseDeVue(id: number) {
    return this.http.get<PriseDeVue>(`${this.serveurUrl}/priseDeVue/${id}`, optionRequete);
  }

  getVue(id: number) {
    return this.http.get<Vue>(`${this.serveurUrl}/vue/${id}`, optionRequete).pipe(
      catchError((err) => {
        console.error(err.error.message);
        return throwError(err.error.message);
      }));
  }

  rechercheToutesLesOuverturesDUnObjectif(id: number): Observable<Ouverture[]> {
    return this.http.get<Ouverture[]>(`${this.serveurUrl}/objectif/${id}/ouvertures`, optionRequete).pipe(
      catchError((err) => {
        console.error(err.error.message);
        return throwError(err.error.message);
      }));
  }

  rechercheToutesLesVitesses(id: number): Observable<Vitesse[]> {
    return this.http.get<Vitesse[]>(`${this.serveurUrl}/objectif/${id}/vitesses}`, optionRequete).pipe(
      catchError((err) => {
        console.error(err.error.message);
        return throwError(err.error.message);
      }));
  }

  rechercheTousLesVues(id: number) {
    return this.http.get<Vue[]>(`${this.serveurUrl}/priseDeVue/${id}/vue`, optionRequete).pipe(
      catchError((err) => {
        console.error(err.error.message);
        return throwError(err.error.message);
      }));
  }

  ajouterVue(vue: ModelVue) {
    return this.http.post<Vue[]>(`${this.serveurUrl}/priseDeVue/${vue.priseDeVue?.id}/vue?appareilPhoto=${vue.appareilPhoto}&film=${vue.film}`,
      optionRequete).pipe(
      catchError((err) => {
        console.error(err.error.message);
        return throwError(err.error.message);
      }));
  }

  rechercheTousLesAppareilsPhotoDUnuePriseDeVue(id: number) {
    return this.http.get<AppareilPhoto[]>(`${this.serveurUrl}/priseDeVue/${id}/appareilPhoto`, optionRequete).pipe(
      catchError((err) => {
        console.error(err.error.message);
        return throwError(err.error.message);
      }));
  }

  rechercheTousLesFilmsDUnuePriseDeVue(id: number) {
    return this.http.get<Film[]>(`${this.serveurUrl}/priseDeVue/${id}/film`, optionRequete).pipe(
      catchError((err) => {
        console.error(err.error.message);
        return throwError(err.error.message);
      }));
  }

  enregistreUnePriseDeVue(priseDeVue: PriseDeVue) {
    return this.http.post<PriseDeVue>(`${this.serveurUrl}/priseDeVue/saisie`, priseDeVue
    ).pipe(
      catchError((err) => {
        console.error(err.error.message);
        return throwError(err.error.message);
      }));
  }

  rechercheTousLesMaterielsDisponible(id: number) {
    return this.http.get<Materiel[]>(`${this.serveurUrl}/priseDeVue/${id}/materielsDisponible`, optionRequete).pipe(
      catchError((err) => {
        console.error(err.error.message);
        return throwError(err.error.message);
      }));
  }

  rechercheTousLesFilmsDisponible(id: number) {
    return this.http.get<Film[]>(`${this.serveurUrl}/priseDeVue/${id}/filmsDisponible`, optionRequete);
  }

  rechercheTousLesProduitsDisponible(id: number) {
    return this.http.get<Produit[]>(`${this.serveurUrl}/priseDeVue/${id}/produitsDisponible`, optionRequete);
  }
}
