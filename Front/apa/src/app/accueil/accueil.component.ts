import { Component, OnInit } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Component({
  selector: "app-accueil",
  templateUrl: "./accueil.component.html",
  styleUrls: ["./accueil.component.css"]
})


export class AccueilComponent implements OnInit {

  // TODO à déplacer en constante globale
  private serveurUrl = `http://127.0.0.1:8181`;

  constructor(
    private http: HttpClient) {
  }

  ngOnInit(): void {
    this.http.get(this.serveurUrl + `/fichiers`).subscribe();
    this.http.get(this.serveurUrl + `/carte`).subscribe();
  }

}
