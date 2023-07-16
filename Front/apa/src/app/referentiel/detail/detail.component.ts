import {Component, Input} from '@angular/core';
import {AppareilPhoto, Materiel} from "../materiel";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent {
  @Input() materiel!: Materiel;
  @Input() appareilPhoto!: AppareilPhoto;
}
