import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Coordonnees, ModelRecherchePriseDeVue, PositionSoleil, Position, PriseDeVue, StatutPriseDeVue} from "./priseDeVue";

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
  private positionUrl = `http:/localhost:8081/position`;

  constructor(
    private http: HttpClient) {
  }

  rechercheTousLesPriseDeVue(): Observable<PriseDeVue[]> {
    return this.http.get<PriseDeVue[]>(this.priseDeVueUrl, optionRequete);
  }

  rechercheDesPriseDeVues(term: ModelRecherchePriseDeVue) {
    return this.http.get<PriseDeVue[]>(`${this.priseDeVueUrl}?nom=${term.nom}&statutPriseDeVue=${term.statutPriseDeVue}&date=${term.date}&remarque=${term.remarque}`, optionRequete);
  }

  rechercheTousLesStatutPriseDeVue(): Observable<StatutPriseDeVue[]> {
    return this.http.get<StatutPriseDeVue[]>(this.statutUrl, optionRequete);
  }

  rechercheCodePostal(coordonnees: Coordonnees) {
    return this.http.get<PriseDeVue[]>(`https://geo.api.gouv.fr/communes?lat=${coordonnees.latitude}&lon=${coordonnees.longitude}&fields=nom,codesPostaux&format=json&geometry=centre`);
  }

  recherchePositionSoleil(coordonnees: Coordonnees) {
    return this.http.get<PositionSoleil>(`https://api.sunrise-sunset.org/json?lat=${coordonnees.latitude}&lng=${coordonnees.longitude}&date=${coordonnees.date}&formatted=0`)
  }

  recherchePosition() {
    return this.http.get<Position[]>(this.positionUrl, optionRequete);
  }

}
