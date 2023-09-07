import {Component, Input} from "@angular/core";
import {AppareilPhoto} from "../../materiel";

@Component({
  selector: "app-materiel-appareil-photo",
  templateUrl: "./materiel.appareil.photo.component.html",
  styleUrls: ["./materiel.appareil.photo.component.css"]
})
export class MaterielAppareilPhotoComponent  {

  @Input() appareilPhoto!: AppareilPhoto;

}
