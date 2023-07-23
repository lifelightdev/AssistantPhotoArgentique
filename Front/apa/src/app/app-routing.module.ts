import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MaterielListComponent} from "./referentiel/materiel/materiel.list.component";
import {DetailMaterielComponent} from "./referentiel/materiel/detail/detail.materiel.component";
import {ProduitListComponent} from "./referentiel/produit/produit.list.component";
import {PriseDeVueListComponent} from "./priseDeVue/priseDeVue.list.component";
import {MapComponent} from "./priseDeVue/detail/map/map.component";
import {RealiserPriseDeVueComponent} from "./priseDeVue/realiser/realiser.priseDeVue.component";

const routes: Routes = [
  {path: 'materiel.list', component: MaterielListComponent},
  {path: 'materiel/detail/:id', component: DetailMaterielComponent},
  {path: 'produit.list', component: ProduitListComponent},
  {path: 'priseDeVue.list', component: PriseDeVueListComponent},
  {path: 'priseDeVue/realiser/:id', component: RealiserPriseDeVueComponent},
  {path: 'map', component: MapComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
