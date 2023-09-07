import {Component, Input} from '@angular/core';
import {Pied} from "../../materiel";

@Component({
  selector: 'app-materiel-pied',
  templateUrl: './materiel.pied.component.html',
  styleUrls: ['./materiel.pied.component.css']
})
export class MaterielPiedComponent {

  @Input() pied!: Pied;

}
