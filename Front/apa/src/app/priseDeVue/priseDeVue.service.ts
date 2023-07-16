import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { ModelRecherchePriseDeVue, PriseDeVue, StatutPriseDeVue} from "./priseDeVue";
import {catchError, tap} from "rxjs/operators";
import {MessageService} from "../message.service";

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
    private http: HttpClient,
    private messageService: MessageService) {
  }

  findAllPriseDeVue(): Observable<PriseDeVue[]> {
    return this.http.get<PriseDeVue[]>(this.priseDeVueUrl, optionRequete);
  }

  searchPriseDeVues(term: ModelRecherchePriseDeVue) {
    return this.http.get<PriseDeVue[]>(`${this.priseDeVueUrl}?nom=${term.nom}&statutPriseDeVue=${term.statutPriseDeVue}&date=${term.date}&remarque=${term.remarque}`, optionRequete).pipe(
      tap(x => x.length ?
        this.log(`found priseDeVue matching "${term}"`) :
        this.log(`no priseDeVues matching "${term}"`)),
      catchError(this.handleError<PriseDeVue[]>('searchPriseDeVues', []))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      return of(result as T);
    };
  }

  private log(message: string) {
    this.messageService.add(`MaterielService: ${message}`);
  }

  findAllStatutPriseDeVue(): Observable<StatutPriseDeVue[]> {
    return this.http.get<StatutPriseDeVue[]>(this.statutUrl, optionRequete);
  }

}
