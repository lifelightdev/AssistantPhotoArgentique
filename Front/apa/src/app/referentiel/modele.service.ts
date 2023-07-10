import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Modele } from './materiel';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const optionRequete = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin':'*'
  })
};

export const modelesUrl = `http://localhost:8081/modele`;

@Injectable({
  providedIn: 'root'
})
export class ModeleService {

  constructor(private http: HttpClient) {
  }

  findAll(): Observable<Modele[]> {
    return this.http.get<Modele[]>(modelesUrl, optionRequete);
  }

}
