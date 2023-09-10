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
    TypeMateriel, Pied, Chassis, Rotule
} from './materiel';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { AppSettings } from "../../AppSettings";

const optionRequete = {
    headers: new HttpHeaders({
        'Access-Control-Allow-Origin': '*'
    })
};

@Injectable({
    providedIn: 'root'
})
export class MaterielService {

    constructor(
        private http: HttpClient) {
    }

    rechercheTousLesMateriels(): Observable<Materiel[]> {
        return this.http.get<Materiel[]>(AppSettings.API_ENDPOINT + `/materiel`, optionRequete);
    }

    rechercheUnMaterielParId(id: number): Observable<Materiel> {
        return this.http.get<Materiel>(AppSettings.API_ENDPOINT + '/materiel/' + id, optionRequete)
    }

    recherchedesMateriels(term: ModelRechercheMateriel): Observable<Materiel[]> {
        return this.http.get<Materiel[]>(`${AppSettings.API_ENDPOINT}/materiel?nom=${term.nom}&typeMateriel=${term.typeMateriel}&sousType=${term.sousType}&statutMateriel=${term.statutMateriel}&marque=${term.marque}&modele=${term.modele}&remarque=${term.remarque}`, optionRequete)
    }

    rechercheTousLesSousType(): Observable<SousType[]> {
        return this.http.get<SousType[]>(AppSettings.API_ENDPOINT + `/sousTypeMateriel`, optionRequete);
    }

    rechercheToutesLesMarques(): Observable<Marque[]> {
        return this.http.get<Marque[]>(AppSettings.API_ENDPOINT + `/marque`, optionRequete);
    }

    rechercheTousLesModeles(): Observable<Modele[]> {
        return this.http.get<Modele[]>(AppSettings.API_ENDPOINT + `/modele`, optionRequete);
    }

    rechercheTousLesTypesDeMateriel(): Observable<TypeMateriel[]> {
        return this.http.get<TypeMateriel[]>(AppSettings.API_ENDPOINT + `/typeMateriel`, optionRequete);
    }

    rechercheTousLesStatutsDeMateriel(): Observable<StatutMateriel[]> {
        return this.http.get<StatutMateriel[]>(AppSettings.API_ENDPOINT + `/statutMateriel`, optionRequete);
    }

    getMateriel(id: number) {
        return this.http.get<Materiel>(AppSettings.API_ENDPOINT + '/materiel/' + id, optionRequete)
    }

    geAppareilPhoto(id: number) {
        return this.http.get<AppareilPhoto>(AppSettings.API_ENDPOINT + '/appareilPhoto/' + id, optionRequete)
    }

    geObjectif(id: number) {
        return this.http.get<Objectif>(AppSettings.API_ENDPOINT + '/objectif/' + id, optionRequete)
    }

    geChassis(id: number) {
        return this.http.get<Chassis>(AppSettings.API_ENDPOINT + '/chassis/' + id, optionRequete)
    }

    gePied(id: number) {
        return this.http.get<Pied>(AppSettings.API_ENDPOINT + '/pied/' + id, optionRequete)
    }

    rechercheTousLesMaterielsDisponible() {
        return this.http.get<Materiel[]>(`${AppSettings.API_ENDPOINT}/materiel?statutMateriel=1`, optionRequete)
    }

    geRotule(idMateriel: number) {
        return this.http.get<Rotule>(AppSettings.API_ENDPOINT + '/rotule/' + idMateriel, optionRequete)
    }
}
