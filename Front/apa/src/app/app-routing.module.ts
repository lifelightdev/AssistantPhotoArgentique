import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ReferentielListComponent} from "./referentiel/referentiel.list.component";

const routes: Routes = [
  { path: 'referentiel.list', component: ReferentielListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
