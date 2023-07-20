import {Materiel} from "../referentiel/materiel/materiel";
import {Produit} from "../referentiel/produit/produit";

export class PriseDeVue {
  id: number | undefined;
  nom: string | undefined;
  statutPriseDeVue: StatutPriseDeVue | undefined;
  date: Date | undefined;
  dateTime: string | undefined;
  position: string | undefined;
  ville: string | undefined;
  codePostal: string | undefined;
  latitude: number | undefined;
  longitude: number | undefined;
  adresse: string | undefined;
  remarque: string | undefined;
  materiels: Materiel[] | undefined;
  produits: Produit[] | undefined;
}

export class StatutPriseDeVue {
  id: number | undefined;
  nom: string | undefined;
}

export class ModelRecherchePriseDeVue {
  nom: string | undefined;
  remarque: string | undefined;
  date: string | undefined;
  position: string | undefined;
  statutPriseDeVue: StatutPriseDeVue | undefined;
}

export class Coordonnees {
  latitude: number | undefined;
  longitude: number | undefined;
  date: string | undefined;
}

export class PositionSoleil {
  results: Results | undefined;
  status: string | undefined;
}

export class Results {
  sunrise: Date | undefined;
  sunset: Date | undefined;
  solar_noon: Date | undefined;
  day_length: number | undefined;
  civil_twilight_begin: Date | undefined;
  civil_twilight_end: Date | undefined;
  nautical_twilight_begin: Date | undefined;
  nautical_twilight_end: Date | undefined;
  astronomical_twilight_begin: Date | undefined;
  astronomical_twilight_end: Date | undefined;
}
