import {Component, Input} from '@angular/core';
import {Chassis} from "../../materiel";

@Component({
  selector: 'app-materiel-chassis',
  templateUrl: './materiel.chassis.component.html',
  styleUrls: ['./materiel.chassis.component.css']
})
export class MaterielChassisComponent {

  @Input() chassis!: Chassis;

}
