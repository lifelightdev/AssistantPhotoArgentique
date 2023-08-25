import { Component, OnInit } from "@angular/core";
import { AppareilPhoto, Materiel, Pied, Objectif } from "../materiel";
import { MaterielService } from "../materiel.service";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: "app-detail-materiel",
  templateUrl: "./detail.materiel.component.html",
  styleUrls: ["./detail.materiel.component.css"]
})
export class DetailMaterielComponent implements OnInit {
  materiel: Materiel | undefined;
  appareilPhoto: AppareilPhoto | undefined;
  pied: Pied | undefined;
  objectif: Objectif | undefined;

  constructor(
    private route: ActivatedRoute,
    private materielService: MaterielService
  ) {}

  ngOnInit(): void {
    const id = parseInt(this.route.snapshot.paramMap.get("id")!, 10);
    this.materielService.getMateriel(id).subscribe(materiel => this.materiel = materiel);
    this.materielService.geAppareilPhoto(id).subscribe(appareilPhoto => {
      this.appareilPhoto = appareilPhoto;
      console.log("this.appareilPhoto.objectif?.ouvetures?.length = " + this.appareilPhoto.objectif?.ouvertures?.length);
    });
    this.materielService.gePied(id).subscribe(pied => this.pied = pied);
    this.materielService.geObjectif(id).subscribe(objectif => this.objectif = objectif);
  }

  protected readonly String = String;
}
