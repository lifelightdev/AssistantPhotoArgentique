import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {
  AppareilPhoto,
  Marque,
  Materiel,
  Modele,
  ModelRechercheMateriel,
  SousType,
  StatutMateriel,
  TypeMateriel
} from './materiel';
import {HttpClient, HttpHeaders} from '@angular/common/http';

const optionRequete = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin':'*'
  })
};

@Injectable({
  providedIn: 'root'
})
export class MaterielService {

  private materielUrl = `http://localhost:8081/materiel`;
  private sousTypeUrl = `http://localhost:8081/sousTypeMateriel`;
  private marqueUrl = `http://localhost:8081/marque`;
  private modeleUrl = `http://localhost:8081/modele`;
  private typeUrl = `http://localhost:8081/typeMateriel`;
  private statutUrl = `http://localhost:8081/statutMateriel`;
  private appareilPhotoUrl = `http://localhost:8081/appareilPhoto`;

  constructor(
    private http: HttpClient) {
  }

  findAllMateriel(): Observable<Materiel[]> {
    return this.http.get<Materiel[]>(this.materielUrl, optionRequete);
  }

  findMaterielById(id: number): Observable<Materiel> {
    return this.http.get<Materiel>(this.materielUrl +'/'+ id, optionRequete)
  }

  searchMateriels(term: ModelRechercheMateriel): Observable<Materiel[]> {
    return this.http.get<Materiel[]>(`${this.materielUrl}?nom=${term.nom}&typeMateriel=${term.typeMateriel}&sousType=${term.sousType}&statutMateriel=${term.statutMateriel}&marque=${term.marque}&modele=${term.modele}&remarque=${term.remarque}`, optionRequete)
  }

  findAllSousType(): Observable<SousType[]> {
    return this.http.get<SousType[]>(this.sousTypeUrl, optionRequete);
  }

  findAllMarque(): Observable<Marque[]> {
    return this.http.get<Marque[]>(this.marqueUrl, optionRequete);
  }

  findAllModele(): Observable<Modele[]> {
    return this.http.get<Modele[]>(this.modeleUrl, optionRequete);
  }
  findAllType(): Observable<TypeMateriel[]> {
    return this.http.get<TypeMateriel[]>(this.typeUrl, optionRequete);
  }

  findAllStatut(): Observable<StatutMateriel[]> {
    return this.http.get<StatutMateriel[]>(this.statutUrl, optionRequete);
  }

  findAppareilPhoto(id: number | undefined): Observable<AppareilPhoto> {
    return this.http.get<AppareilPhoto>(this.appareilPhotoUrl +'/'+ id, optionRequete);
  }

}
