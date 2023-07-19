import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PopupService {

  constructor() { }

  makePhotoPopup(data: any): string {
    return `<div>Nom : ${data.nom}</div>` + `<div>Adresse : ${data.adresse}</div>`
  }
}
