import {Component, Input, OnInit} from '@angular/core';
import {AppareilPhoto, Materiel} from "../materiel";
import {MaterielService} from "../materiel.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-detail-materiel',
  templateUrl: './detail.materiel.component.html',
  styleUrls: ['./detail.materiel.component.css']
})
export class DetailMaterielComponent implements OnInit {
  materiel: Materiel | undefined;

  @Input() appareilPhoto!: AppareilPhoto;

  constructor(
    private route: ActivatedRoute,
    private materielService: MaterielService
  ) {}

  ngOnInit(): void {
    this.getMateriel();
  }

  getMateriel(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.materielService.getMateriel(id).subscribe(materiel => this.materiel = materiel);
  }

}
