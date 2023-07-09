import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Marque } from './marque';
import {HttpClient, HttpHeaders} from '@angular/common/http';

const optionRequete = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin':'*'
  })
};

export const marquesUrl = `http://localhost:8081/marque`;

@Injectable({
  providedIn: 'root'
})
export class MarqueService {

  constructor(private http: HttpClient) {
  }

  findAll(): Observable<Marque[]> {
    return this.http.get<Marque[]>(marquesUrl, optionRequete);
  }

}
