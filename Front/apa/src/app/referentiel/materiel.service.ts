import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Materiel, SousType } from './materiel';
import {HttpClient, HttpHeaders} from '@angular/common/http';

const optionRequete = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin':'*'
  })
};

export const materielsUrl = `http://localhost:8081/materiel`;
export const sousTypeMaterielsUrl = `http://localhost:8081/sousTypeMateriel`;

@Injectable({
  providedIn: 'root'
})
export class MaterielService {

  constructor(private http: HttpClient) {
  }

  findAllMateriel(): Observable<Materiel[]> {
    return this.http.get<Materiel[]>(materielsUrl, optionRequete);
  }

  findAllSousTypeMateriel(): Observable<SousType[]> {
    return this.http.get<SousType[]>(sousTypeMaterielsUrl, optionRequete);
  }

}
