import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MaterielListComponent} from "./referentiel/materiel/materiel.list.component";
import {ProduitListComponent} from "./referentiel/produit/produit.list.component";
import {PriseDeVueListComponent} from "./priseDeVue/priseDeVue.list.component";
import {MapComponent} from "./priseDeVue/detail/map/map.component";

const routes: Routes = [
  {path: 'materiel.list', component: MaterielListComponent},
  {path: 'produit.list', component: ProduitListComponent},
  {path: 'priseDeVue.list', component: PriseDeVueListComponent},
  {path: 'map', component: MapComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
