import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Materiel } from './materiel';
import {HttpClient, HttpHeaders} from '@angular/common/http';

const optionRequete = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin':'*'
  })
};

export const materielsUrl = `http://localhost:8081/materiel`;

@Injectable({
  providedIn: 'root'
})
export class MaterielService {

  constructor(private http: HttpClient) {
  }

  findAll(): Observable<Materiel[]> {
    return this.http.get<Materiel[]>(materielsUrl, optionRequete);
  }

}
