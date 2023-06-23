import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Materiel} from "./materiel";
import {MaterielService} from "./materiel.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-referentiel.list',
  templateUrl: './referentiel.list.component.html',
  styleUrls: ['./referentiel.list.component.css']
})
export class ReferentielListComponent implements OnInit, AfterViewInit {

  constructor(private materielService: MaterielService) { }

  materiels: Materiel[] = [];
  ngOnInit(): void {
    this.materielService.findAll().subscribe(data => {
      this.dataSource.data = data;
    });
  }

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  displayedColumns = ["Identifiant", "Nom", "Type", "Sous type", "Status", "Marque", "Modéle", "Photo", "Mode d'emploie", "Remarque"];
  dataSource = new MatTableDataSource<Materiel>();

}
