import {Component, OnInit} from "@angular/core";
import {AppareilPhoto, Materiel, Pied, Objectif, Chassis, Rotule} from "../materiel";
import {MaterielService} from "../materiel.service";
import {ActivatedRoute} from "@angular/router";

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
    chassis: Chassis | undefined;
    rotule: Rotule | undefined;

    constructor(
        private route: ActivatedRoute,
        private materielService: MaterielService
    ) {
    }

    ngOnInit(): void {
        const idMateriel = parseInt(this.route.snapshot.paramMap.get("id")!, 10);
        this.materielService.getMateriel(idMateriel).subscribe(materiel => this.materiel = materiel);
        this.materielService.geAppareilPhoto(idMateriel).subscribe(appareilPhoto => this.appareilPhoto = appareilPhoto);
        this.materielService.gePied(idMateriel).subscribe(pied => this.pied = pied);
        this.materielService.geObjectif(idMateriel).subscribe(objectif => this.objectif = objectif);
        this.materielService.geChassis(idMateriel).subscribe(chassis => this.chassis = chassis);
        this.materielService.geRotule(idMateriel).subscribe(rotule => this.rotule = rotule);
    }

}
