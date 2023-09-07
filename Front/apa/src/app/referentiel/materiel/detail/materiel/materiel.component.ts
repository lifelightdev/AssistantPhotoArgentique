import {Component, Input} from '@angular/core';
import {Materiel} from "../../materiel";

@Component({
  selector: 'app-materiel',
  templateUrl: './materiel.component.html',
  styleUrls: ['./materiel.component.css']
})
export class MaterielComponent {

  @Input() materiel!: Materiel;

}
