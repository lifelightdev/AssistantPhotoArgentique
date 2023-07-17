import {Materiel} from "../referentiel/materiel/materiel";
import {Produit} from "../referentiel/produit/produit";

export class PriseDeVue {
  id: number | undefined;
  nom: string | undefined;
  statutPriseDeVue: StatutPriseDeVue | undefined;
  date: Date | undefined;
  position: string | undefined;
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
  date: Date | undefined;
  position: string | undefined;
  statutPriseDeVue: StatutPriseDeVue | undefined;
}
