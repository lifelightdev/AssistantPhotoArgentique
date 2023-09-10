import { Component, OnInit } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { AppSettings } from "../AppSettings";

@Component({
  selector: "app-accueil",
  templateUrl: "./accueil.component.html",
  styleUrls: ["./accueil.component.css"]
})

export class AccueilComponent implements OnInit {

  constructor(
    private http: HttpClient) {
  }

  ngOnInit(): void {
    this.http.get(AppSettings.API_ENDPOINT + `/fichiers`).subscribe();
    this.http.get(AppSettings.API_ENDPOINT + `/carte`).subscribe();
  }

}
