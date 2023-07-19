import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import * as L from 'leaflet';
import { PopupService } from './popup.service';

@Injectable({
  providedIn: 'root'
})
export class MarkerService {

  photos: string = '/assets/Data/photo.geojson';

  constructor(private http: HttpClient,
              private popupService: PopupService) { }

  makePhotoMarkers(map: L.Map): void {
    this.http.get(this.photos).subscribe((res: any) => {
      for (const c of res.features) {
        const lon = c.geometry.coordinates[0];
        const lat = c.geometry.coordinates[1];
        const marker = L.marker([lat, lon]);
        marker.bindPopup(this.popupService.makePhotoPopup(c.properties));
        marker.addTo(map);
      }
    });
  }

}
