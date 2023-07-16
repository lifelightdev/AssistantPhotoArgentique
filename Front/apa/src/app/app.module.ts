import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClientModule} from "@angular/common/http";
import {MatButtonModule} from "@angular/material/button";
import {MatMenuModule} from "@angular/material/menu";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatCardModule} from "@angular/material/card";
import {MatListModule} from "@angular/material/list";
import {HeaderComponent} from './header/header.component';
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatTableModule} from "@angular/material/table";
import {MatInputModule} from "@angular/material/input";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatSelectModule} from "@angular/material/select";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {DetailMaterielComponent} from "./referentiel/materiel/detail/detail.materiel.component";
import {MaterielListComponent} from "./referentiel/materiel/materiel.list.component";
import {ProduitListComponent} from "./referentiel/produit/produit.list.component";
import {DetailProduitComponent} from "./referentiel/produit/detail/detail.produit.component";
import {PriseDeVueListComponent} from "./priseDeVue/priseDeVue.list.component";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MAT_DATE_LOCALE, MatNativeDateModule} from '@angular/material/core';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MaterielListComponent,
    DetailMaterielComponent,
    ProduitListComponent,
    DetailProduitComponent,
    PriseDeVueListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    MatToolbarModule,
    MatCardModule,
    HttpClientModule,
    MatListModule,
    MatPaginatorModule,
    MatTableModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  providers: [ {provide: MAT_DATE_LOCALE, useValue: 'fr-FR'}, ],
  bootstrap: [AppComponent]
})
export class AppModule { }
