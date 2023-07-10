import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TypeMateriel } from './materiel';
import {HttpClient, HttpHeaders} from '@angular/common/http';

const optionRequete = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin':'*'
  })
};

export const typesUrl = `http://localhost:8081/typeMateriel`;

@Injectable({
  providedIn: 'root'
})
export class TypeMaterielService {

  constructor(private http: HttpClient) {
  }

  findAll(): Observable<TypeMateriel[]> {
    return this.http.get<TypeMateriel[]>(typesUrl, optionRequete);
  }
}
