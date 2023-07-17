import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ModelRecherchePriseDeVue, PriseDeVue, StatutPriseDeVue} from "./priseDeVue";

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
    return this.http.get<PriseDeVue[]>(`${this.priseDeVueUrl}?nom=${term.nom}&statutPriseDeVue=${term.statutPriseDeVue}&date=${term.date?.toLocaleDateString()}&remarque=${term.remarque}`, optionRequete);
  }

  findAllStatutPriseDeVue(): Observable<StatutPriseDeVue[]> {
    return this.http.get<StatutPriseDeVue[]>(this.statutUrl, optionRequete);
  }

}
