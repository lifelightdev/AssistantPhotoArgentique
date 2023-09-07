import {Component, Input} from '@angular/core';
import {Objectif} from "../../materiel";

@Component({
  selector: 'app-materiel-objectif',
  templateUrl: './materiel.objectif.component.html',
  styleUrls: ['./materiel.objectif.component.css']
})
export class MaterielObjectifComponent {

  @Input() objectif!: Objectif;

}
