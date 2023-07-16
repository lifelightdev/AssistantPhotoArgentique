import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MaterielListComponent} from "./referentiel/materiel/materiel.list.component";
import {ProduitListComponent} from "./referentiel/produit/produit.list.component";

const routes: Routes = [
  {path: 'materiel.list', component: MaterielListComponent},
  {path: 'produit.list', component: ProduitListComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
