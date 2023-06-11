import {Component, OnInit} from '@angular/core';
import {Materiel} from "./materiel";
import {MaterielService} from "./materiel.service";

@Component({
  selector: 'app-referentiel.list',
  templateUrl: './referentiel.list.component.html',
  styleUrls: ['./referentiel.list.component.css']
})
export class ReferentielListComponent implements OnInit {

  constructor(private materielService: MaterielService) { }

  materiels: Materiel[] | undefined;
  ngOnInit(): void {
    this.materielService.findAll().subscribe(data => {
      console.log(data);
      this.materiels = data;
    });
  }
}
