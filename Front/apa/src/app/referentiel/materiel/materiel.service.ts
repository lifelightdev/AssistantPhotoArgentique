import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {
  AppareilPhoto,
  Objectif,
  Marque,
  Materiel,
  Modele,
  ModelRechercheMateriel,
  SousType,
  StatutMateriel,
  TypeMateriel, Pied
} from './materiel';
import {HttpClient, HttpHeaders} from '@angular/common/http';

const optionRequete = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin': '*'
  })
};

@Injectable({
  providedIn: 'root'
})
export class MaterielService {

  private serveurUrl = `http://localhost:8081`;

  constructor(
    private http: HttpClient) {
  }

  rechercheTousLesMateriel(): Observable<Materiel[]> {
    return this.http.get<Materiel[]>(this.serveurUrl + `/materiel`, optionRequete);
  }

  rechercheUnMaterielParId(id: number): Observable<Materiel> {
    return this.http.get<Materiel>(this.serveurUrl + '/materiel/' + id, optionRequete)
  }

  recherchedesMateriels(term: ModelRechercheMateriel): Observable<Materiel[]> {
    return this.http.get<Materiel[]>(`${this.serveurUrl}/materiel?nom=${term.nom}&typeMateriel=${term.typeMateriel}&sousType=${term.sousType}&statutMateriel=${term.statutMateriel}&marque=${term.marque}&modele=${term.modele}&remarque=${term.remarque}`, optionRequete)
  }

  rechercheTousLesSousType(): Observable<SousType[]> {
    return this.http.get<SousType[]>(this.serveurUrl + `/sousTypeMateriel`, optionRequete);
  }

  rechercheToutesLesMarques(): Observable<Marque[]> {
    return this.http.get<Marque[]>(this.serveurUrl + `/marque`, optionRequete);
  }

  rechercheTousLesModeles(): Observable<Modele[]> {
    return this.http.get<Modele[]>(this.serveurUrl + `/modele`, optionRequete);
  }

  rechercheTousLesTypesDeMateriel(): Observable<TypeMateriel[]> {
    return this.http.get<TypeMateriel[]>(this.serveurUrl + `/typeMateriel`, optionRequete);
  }

  rechercheTousLesStatutsDeMateriel(): Observable<StatutMateriel[]> {
    return this.http.get<StatutMateriel[]>(this.serveurUrl + `/statutMateriel`, optionRequete);
  }

  getMateriel(id: number) {
    return this.http.get<Materiel>(this.serveurUrl + '/materiel/' + id, optionRequete)
  }

  geAppareilPhoto(id: number) {
    return this.http.get<AppareilPhoto>(this.serveurUrl + '/appareilPhoto/' + id, optionRequete)
  }

  geObjectif(id: number) {
    return this.http.get<Objectif>(this.serveurUrl + '/objectif/' + id, optionRequete)
  }

  gePied(id: number) {
    return this.http.get<Pied>(this.serveurUrl + '/pied/' + id, optionRequete)
  }

}
