import {Component, Input} from '@angular/core';
import {Chassis} from "../materiel";

@Component({
  selector: 'app-detail-chassis',
  templateUrl: './detail.chassis.component.html',
  styleUrls: ['./detail.chassis.component.css']
})
export class DetailChassisComponent {

  @Input() chassis!: Chassis;

}
