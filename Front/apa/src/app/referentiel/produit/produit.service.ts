import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {MessageService} from "../../message.service";
import {Marque, Modele, ModelRechercheProduit, Produit, StatutProduit, TypeProduit} from "./produit";
import {catchError, tap} from "rxjs/operators";

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
    private http: HttpClient,
    private messageService: MessageService) {
  }

  findAllProduit(): Observable<Produit[]> {
    return this.http.get<Produit[]>(this.produitUrl, optionRequete);
  }

  searchProduits(term: ModelRechercheProduit) {
    return this.http.get<Produit[]>(`${this.produitUrl}?nom=${term.nom}&typeProduit=${term.typeProduit}&statutProduit=${term.statutProduit}&marque=${term.marque}&modele=${term.modele}&remarque=${term.remarque}`, optionRequete).pipe(
      tap(x => x.length ?
        this.log(`found produit matching "${term}"`) :
        this.log(`no produits matching "${term}"`)),
      catchError(this.handleError<Produit[]>('searchProduits', []))
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
