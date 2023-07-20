import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Coordonnees, ModelRecherchePriseDeVue, PositionSoleil, PriseDeVue, StatutPriseDeVue} from "./priseDeVue";

const optionRequete = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin': '*'
  })
};

@Injectable({
  providedIn: 'root'
})

export class PriseDeVueService {

  private priseDeVueUrl = `http://localhost:8081/priseDeVue`;
  private statutUrl = `http://localhost:8081/statutPriseDeVue`;

  constructor(
    private http: HttpClient) {
  }

  findAllPriseDeVue(): Observable<PriseDeVue[]> {
    return this.http.get<PriseDeVue[]>(this.priseDeVueUrl, optionRequete);
  }

  searchPriseDeVues(term: ModelRecherchePriseDeVue) {
    return this.http.get<PriseDeVue[]>(`${this.priseDeVueUrl}?nom=${term.nom}&statutPriseDeVue=${term.statutPriseDeVue}&date=${term.date}&remarque=${term.remarque}`, optionRequete);
  }

  findAllStatutPriseDeVue(): Observable<StatutPriseDeVue[]> {
    return this.http.get<StatutPriseDeVue[]>(this.statutUrl, optionRequete);
  }

  rechercheCodePostal(coordonnees: Coordonnees) {
    return this.http.get<PriseDeVue[]>(`https://geo.api.gouv.fr/communes?lat=${coordonnees.latitude}&lon=${coordonnees.longitude}&fields=nom,codesPostaux&format=json&geometry=centre`);
  }

  recherchePositionSoleil(coordonnees: Coordonnees){
    return this.http.get<PositionSoleil>(`https://api.sunrise-sunset.org/json?lat=${coordonnees.latitude}&lng=${coordonnees.longitude}&date=${coordonnees.date}&formatted=0`)
  }

}
