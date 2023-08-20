import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { MaterielListComponent } from "./referentiel/materiel/materiel.list.component";
import { DetailMaterielComponent } from "./referentiel/materiel/detail/detail.materiel.component";
import { ProduitListComponent } from "./referentiel/produit/produit.list.component";
import { PriseDeVueListComponent } from "./priseDeVue/priseDeVue.list.component";
import { MapComponent } from "./priseDeVue/detail/map/map.component";
import { DetailProduitComponent } from "./referentiel/produit/detail/detail.produit.component";
import { DetailPriseDeVueComponent } from "./priseDeVue/detail/detail.priseDeVue.component";
import { AccueilComponent } from "./accueil/accueil.component";
import { SaisiePriseDeVueComponent } from "./priseDeVue/saisie/saisie.priseDeVue.component";

const routes: Routes = [
  { path: "", redirectTo: "/accueil", pathMatch: "full" },
  { path: "accueil", component: AccueilComponent },
  { path: "materiel.list", component: MaterielListComponent },
  { path: "materiel/detail/:id", component: DetailMaterielComponent },
  { path: "produit.list", component: ProduitListComponent },
  { path: "produit/detail/:id", component: DetailProduitComponent },
  { path: "priseDeVue/list", component: PriseDeVueListComponent },
  { path: "priseDeVue/detail/:id/:latitude/:longitude/:date", component: DetailPriseDeVueComponent },
  { path: "priseDeVue/saisie", component: SaisiePriseDeVueComponent },
  { path: "map", component: MapComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
