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

  private serveurUrl = `http://localhost:8081`;

  constructor(
    private http: HttpClient) {
  }

  rechercheTousLesPriseDeVue(): Observable<PriseDeVue[]> {
    return this.http.get<PriseDeVue[]>(this.serveurUrl +`/priseDeVue`, optionRequete);
  }

  rechercheDesPriseDeVues(term: ModelRecherchePriseDeVue) {
    return this.http.get<PriseDeVue[]>(`${this.serveurUrl}/priseDeVue?nom=${term.nom}&statutPriseDeVue=${term.statutPriseDeVue}&date=${term.date}&remarque=${term.remarque}`, optionRequete);
  }

  rechercheTousLesStatutPriseDeVue(): Observable<StatutPriseDeVue[]> {
    return this.http.get<StatutPriseDeVue[]>(this.serveurUrl + `/statutPriseDeVue`, optionRequete);
  }

  rechercheCodePostal(coordonnees: Coordonnees) {
    return this.http.get<PriseDeVue[]>(`https://geo.api.gouv.fr/communes?lat=${coordonnees.latitude}&lon=${coordonnees.longitude}&fields=nom,codesPostaux&format=json&geometry=centre`);
  }

  recherchePositionSoleil(coordonnees: Coordonnees) {
    return this.http.get<PositionSoleil>(`https://api.sunrise-sunset.org/json?lat=${coordonnees.latitude}&lng=${coordonnees.longitude}&date=${coordonnees.date}&formatted=0`)
  }

  recherchePosition() {
    return this.http.get<Position[]>(this.serveurUrl + `/position`, optionRequete);
  }

}
