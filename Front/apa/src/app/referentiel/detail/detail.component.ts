import {Component, inject} from '@angular/core';
import {MaterielService} from "../materiel.service";
import {Materiel} from "../materiel";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent {

  route: ActivatedRoute = inject(ActivatedRoute);
  materielService = inject(MaterielService);
  materiel= new Materiel ();

  constructor() {
    const materielId = parseInt(this.route.snapshot.params['id'], 10);
    this.materielService.findMaterielById(materielId)
      .subscribe(materiels => {
        this.materiel = materiels as Materiel
      })
  }

}
