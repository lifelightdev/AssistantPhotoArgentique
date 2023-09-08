import {Component, Input} from '@angular/core';
import {Materiel} from "../materiel";

@Component({
  selector: 'app-detail-materiel',
  templateUrl: './detail.materiel.component.html',
  styleUrls: ['./detail.materiel.component.css']
})
export class DetailMaterielComponent {

  @Input() materiel!: Materiel;

}
