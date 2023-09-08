import {Component, Input} from '@angular/core';
import {Pied} from "../materiel";

@Component({
  selector: 'app-detail-pied',
  templateUrl: './detail.pied.component.html',
  styleUrls: ['./detail.pied.component.css']
})
export class DetailPiedComponent {

  @Input() pied!: Pied;

}
