import {Injectable} from '@angular/core';
import {catchError, Observable, throwError} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {
  ModelRecherchePriseDeVue,
  PositionSoleil,
  Position,
  PriseDeVue,
  StatutPriseDeVue, Vue, ModelVue
} from "./priseDeVue";
import {AppareilPhoto, Ouverture, Vitesse} from "../referentiel/materiel/materiel";
import {Film} from "../referentiel/produit/produit";

const optionRequete = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin': '*',
    'Content-Type': 'application/json',
  })
};

@Injectable({
  providedIn: 'root'
})

export class PriseDeVueService {

  private serveurUrl = `http://127.0.0.1:8081`;

  constructor(
    private http: HttpClient) {
  }

  rechercheTousLesPriseDeVue(): Observable<PriseDeVue[]> {
    return this.http.get<PriseDeVue[]>(this.serveurUrl + `/priseDeVue`, optionRequete);
  }

  rechercheDesPriseDeVues(term: ModelRecherchePriseDeVue) {
    return this.http.get<PriseDeVue[]>(`${this.serveurUrl}/priseDeVue?nom=${term.nom}&statutPriseDeVue=${term.statutPriseDeVue}&date=${term.date}&remarque=${term.remarque}`, optionRequete);
  }

  rechercheTousLesStatutPriseDeVue(): Observable<StatutPriseDeVue[]> {
    return this.http.get<StatutPriseDeVue[]>(this.serveurUrl + `/statutPriseDeVue`, optionRequete);
  }

  rechercheCodePostal(latitude: number, longitude: number) {
    return this.http.get<PriseDeVue[]>(`https://geo.api.gouv.fr/communes?lat=${latitude}&lon=${longitude}&fields=nom,codesPostaux&format=json&geometry=centre`);
  }

  recherchePositionSoleil(id: number, latitude: number, longitude: number, date: string) {
    return this.http.get<PositionSoleil>(`https://api.sunrise-sunset.org/json?lat=${latitude}&lng=${longitude}&date=${date}&formatted=0`)
  }

  recherchePosition() {
    return this.http.get<Position[]>(this.serveurUrl + `/position`, optionRequete);
  }

  getPriseDeVue(id: number) {
    return this.http.get<PriseDeVue>(this.serveurUrl + '/priseDeVue/' + id, optionRequete)
  }

  getVue(id: number) {
    return this.http.get<Vue>(this.serveurUrl + '/vue/' + id, optionRequete)
  }

  rechercheToutesLesOuvertures(id: number): Observable<Ouverture[]> {
    return this.http.get<Ouverture[]>(this.serveurUrl + `/ouvertures/` + id, optionRequete);
  }

  rechercheToutesLesVitesses(id: number): Observable<Vitesse[]> {
    return this.http.get<Vitesse[]>(this.serveurUrl + `/vitesses/` + id, optionRequete);
  }

  rechercheTousLesVues(id: number) {
    return this.http.get<Vue[]>(this.serveurUrl + '/priseDeVue/' + id + '/vue', optionRequete);
  }

  ajouterVue(vue: ModelVue,) {
    return this.http.post<Vue[]>(`${this.serveurUrl}/vue?priseDeVue=${vue.id}&appareilPhoto=${vue.appareilPhoto}&film=${vue.film}`,
      optionRequete).pipe(
      catchError((err) => {
        console.error(err.error.message);
        return throwError(err.error.message);
      }));
  }

  rechercheTousLesAppareilsPhotoDUnuePriseDeVue(id: number) {
    return this.http.get<AppareilPhoto[]>(this.serveurUrl + '/priseDeVue/' + id + '/appareilPhoto', optionRequete);
  }

  rechercheTousLesFilmsDUnuePriseDeVue(id: number) {
    return this.http.get<Film[]>(this.serveurUrl + '/priseDeVue/' + id + '/film', optionRequete);
  }
}
