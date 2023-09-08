import {Component, Input} from '@angular/core';
import {Objectif} from "../materiel";

@Component({
  selector: 'app-detail-objectif',
  templateUrl: './detail.objectif.component.html',
  styleUrls: ['./detail.objectif.component.css']
})
export class DetailObjectifComponent {

  @Input() objectif!: Objectif;

}
