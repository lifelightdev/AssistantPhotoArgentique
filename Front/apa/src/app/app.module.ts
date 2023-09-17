import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { MatButtonModule } from "@angular/material/button";
import { MatMenuModule } from "@angular/material/menu";
import { MatIconModule } from "@angular/material/icon";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatCardModule } from "@angular/material/card";
import { MatListModule } from "@angular/material/list";
import { HeaderComponent } from "./header/header.component";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatTableModule } from "@angular/material/table";
import { MatInputModule } from "@angular/material/input";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatSelectModule } from "@angular/material/select";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatDialogModule } from "@angular/material/dialog";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MaterielComponent } from "./referentiel/materiel/detail/materiel.component";
import { DetailAppareilPhotoComponent } from "./referentiel/materiel/detail/detail.appareil.photo.component";
import { MaterielListComponent } from "./referentiel/materiel/materiel.list.component";
import { ProduitListComponent } from "./referentiel/produit/produit.list.component";
import { DetailProduitComponent } from "./referentiel/produit/detail/detail.produit.component";
import { PriseDeVueListComponent } from "./priseDeVue/priseDeVue.list.component";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MAT_DATE_LOCALE, MatNativeDateModule, MatRippleModule } from "@angular/material/core";
import { DetailPriseDeVueComponent } from "./priseDeVue/detail/detail.priseDeVue.component";
import { MapComponent } from "./priseDeVue/detail/map/map.component";
import { paginationPersonnalise } from "./paginationPersonnalise";
import { MatPaginatorIntl } from "@angular/material/paginator";
import { TranslateLoader, TranslateModule } from "@ngx-translate/core";
import { TranslateHttpLoader } from "@ngx-translate/http-loader";
import { HttpClient, HttpClientModule } from "@angular/common/http";
import { AccueilComponent } from "./accueil/accueil.component";
import { SaisiePriseDeVueComponent } from "./priseDeVue/saisie/saisie.priseDeVue.component";
import { DetailObjectifComponent } from "./referentiel/materiel/detail/detail.objectif.component";
import { DetailChassisComponent } from "./referentiel/materiel/detail/detail.chassis.component";
import { DetailMaterielComponent } from "./referentiel/materiel/detail/detail.materiel.component";
import { DetailPiedComponent } from "./referentiel/materiel/detail/detail.pied.component";
import { DetailRotuleComponent } from "./referentiel/materiel/detail/detail.rotule.component";
import {NgOptimizedImage} from "@angular/common";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MaterielListComponent,
    MaterielComponent,
    DetailAppareilPhotoComponent,
    ProduitListComponent,
    DetailProduitComponent,
    PriseDeVueListComponent,
    DetailPriseDeVueComponent,
    MapComponent,
    AccueilComponent,
    SaisiePriseDeVueComponent,
    DetailObjectifComponent,
    DetailChassisComponent,
    DetailMaterielComponent,
    DetailPiedComponent,
    DetailRotuleComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatButtonModule,
        MatInputModule,
        MatRippleModule,
        MatMenuModule,
        MatIconModule,
        MatToolbarModule,
        MatCardModule,
        MatListModule,
        MatPaginatorModule,
        MatTableModule,
        MatProgressSpinnerModule,
        MatSelectModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatDialogModule,
        FormsModule,
        ReactiveFormsModule,
        // ngx-translate and the loader module
        HttpClientModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: HttpLoaderFactory,
                deps: [HttpClient]
            }
        }),
        NgOptimizedImage
    ],
  exports: [
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatRippleModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: "fr-FR" },
    { provide: MatPaginatorIntl, useClass: paginationPersonnalise }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient): TranslateHttpLoader {
  return new TranslateHttpLoader(http);
}
