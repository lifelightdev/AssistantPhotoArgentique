import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Marque, Materiel, Modele, SousType, StatutMateriel, TypeMateriel} from './materiel';
import {HttpClient, HttpHeaders} from '@angular/common/http';

const optionRequete = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin':'*'
  })
};

export const materielUrl = `http://localhost:8081/materiel`;
export const sousTypeUrl = `http://localhost:8081/sousTypeMateriel`;
export const marqueUrl = `http://localhost:8081/marque`;
export const modeleUrl = `http://localhost:8081/modele`;
export const typeUrl = `http://localhost:8081/typeMateriel`;
export const statutUrl = `http://localhost:8081/statut`;

@Injectable({
  providedIn: 'root'
})
export class MaterielService {

  constructor(private http: HttpClient) {
  }

  findAllMateriel(): Observable<Materiel[]> {
    return this.http.get<Materiel[]>(materielUrl, optionRequete);
  }

  findMaterielById(id: number): Observable<Materiel> {
    return this.http.get<Materiel>(materielUrl +'/'+ id)
  }


  findAllSousType(): Observable<SousType[]> {
    return this.http.get<SousType[]>(sousTypeUrl, optionRequete);
  }

  findAllMarque(): Observable<Marque[]> {
    return this.http.get<Marque[]>(marqueUrl, optionRequete);
  }

  findAllModele(): Observable<Modele[]> {
    return this.http.get<Modele[]>(modeleUrl, optionRequete);
  }
  findAllType(): Observable<TypeMateriel[]> {
    return this.http.get<TypeMateriel[]>(typeUrl, optionRequete);
  }

  findAllStatut(): Observable<StatutMateriel[]> {
    return this.http.get<StatutMateriel[]>(statutUrl, optionRequete);
  }
}
