import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ReferentielListComponent} from "./referentiel/referentiel.list.component";
import {DetailComponent} from "./referentiel/detail/detail.component";

const routes: Routes = [
  { path: 'referentiel.list', component: ReferentielListComponent },
  { path: 'referentiel/details/:id', component: DetailComponent, title: 'Détail du matériel' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
