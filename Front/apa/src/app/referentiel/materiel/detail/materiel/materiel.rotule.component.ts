import {Component, Input} from '@angular/core';
import {Rotule} from "../../materiel";

@Component({
  selector: 'app-materiel-rotule',
  templateUrl: './materiel.rotule.component.html',
  styleUrls: ['./materiel.rotule.component.css']
})
export class MaterielRotuleComponent {

  @Input() rotule!: Rotule;

  protected readonly String = String;
}
