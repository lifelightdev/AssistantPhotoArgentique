import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from "@angular/common/http";
import { MatButtonModule }  from "@angular/material/button";
import { MatMenuModule }    from "@angular/material/menu";
import { MatIconModule }    from "@angular/material/icon";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatCardModule }    from "@angular/material/card";
import { MatListModule }    from "@angular/material/list";
import { HeaderComponent } from './header/header.component';
import { ReferentielListComponent } from './referentiel/referentiel.list.component';
import { MaterielService } from "./referentiel/materiel.service";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatTableModule } from "@angular/material/table";
import { MatInputModule } from "@angular/material/input";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatSelectModule } from "@angular/material/select";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import {DetailComponent} from "./referentiel/detail/detail.component";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ReferentielListComponent,
    DetailComponent,
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
    ReactiveFormsModule
  ],
  providers: [MaterielService],
  bootstrap: [AppComponent]
})
export class AppModule { }
