import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from "@angular/material/button";
import {MatMenuModule} from "@angular/material/menu";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatCardModule} from "@angular/material/card";
import { HeaderComponent } from './header/header.component';
import { ReferentielListComponent } from './referentiel/referentiel.list.component';
import {HttpClientModule} from "@angular/common/http";
import {MaterielService} from "./referentiel/materiel.service";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ReferentielListComponent,
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
  ],
  providers: [MaterielService],
  bootstrap: [AppComponent]
})
export class AppModule { }
