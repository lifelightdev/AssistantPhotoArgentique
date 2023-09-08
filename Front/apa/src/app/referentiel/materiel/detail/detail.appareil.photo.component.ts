import {Component, Input} from "@angular/core";
import {AppareilPhoto} from "../materiel";

@Component({
  selector: "app-detail-appareil-photo",
  templateUrl: "./detail.appareil.photo.component.html",
  styleUrls: ["./detail.appareil.photo.component.css"]
})
export class DetailAppareilPhotoComponent {

  @Input() appareilPhoto!: AppareilPhoto;

}
