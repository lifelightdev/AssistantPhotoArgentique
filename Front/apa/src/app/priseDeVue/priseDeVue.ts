import {AppareilPhoto, Materiel, Ouverture, Vitesse} from "../referentiel/materiel/materiel";
import {Film} from "../referentiel/produit/produit";

export class PriseDeVue {
  id: number | undefined;
  nom: string | undefined;
  statutPriseDeVue: StatutPriseDeVue | undefined;
  date: Date | undefined;
  position: Position | undefined;
  remarque: string | undefined;
  materiels: Materiel[] | undefined;
  films: Film[] | undefined;
  vues: Vue[] | undefined;
}

export class Position {
  id: number | undefined;
  nom: string | undefined;
  ville: string | undefined;
  codePostal: string | undefined;
  latitude: number | undefined;
  longitude: number | undefined;
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

export class Vue {
  id: number | undefined;
  nom: string | undefined;
  priseDeVue: PriseDeVue | undefined;
  statutVue: StatutVue | undefined;
  appareilPhoto: AppareilPhoto | undefined;
  position: Position | undefined;
  film: Film | undefined;
  ouverture: Ouverture | undefined;
  vitesse: Vitesse | undefined;
  photo: undefined;
}

export class StatutVue {
  id: number | undefined;
  nom: string | undefined;
}

export class ModelVue {
  id = 0;
  appareilPhoto: number | undefined;
  film: number | undefined;
}
