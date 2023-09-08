import {Component, Input} from '@angular/core';
import {Rotule} from "../materiel";

@Component({
  selector: 'app-detail-rotule',
  templateUrl: './detail.rotule.component.html',
  styleUrls: ['./detail.rotule.component.css']
})
export class DetailRotuleComponent {

  @Input() rotule!: Rotule;

  protected readonly String = String;
}
