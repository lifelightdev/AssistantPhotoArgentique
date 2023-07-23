export class Produit {
  id: number | undefined;
  nom: string | undefined;
  typeProduit: TypeProduit | undefined;
  statutProduit: StatutProduit | undefined;
  modele: Modele | undefined;
  photo: undefined;
  modeEmploie: undefined;
  remarque: string | undefined;
}

export class TypeProduit {
  id: number | undefined;
  nom: string | undefined;
}

export class StatutProduit {
  id: number | undefined;
  nom: string | undefined;
}

export class Marque {
  id: number | undefined;
  nom: string | undefined;
}

export class Modele {
  id: number | undefined;
  nom: string | undefined;
  marque: Marque | undefined;
}

export class ModelRechercheProduit {
  nom: string | undefined;
  marque: Marque | undefined;
  modele: Modele | undefined;
  remarque: string | undefined;
  statutProduit: StatutProduit | undefined;
  typeProduit: TypeProduit | undefined;
}

export class Film {
  id: number | undefined;
  produit: Produit | undefined;
  statutFilm: StatutFilm | undefined;
  tailleFilm: TailleFilm | undefined;
  typeFilm: TypeFilm | undefined;
  tailleVue: TailleVue | undefined;
  nbVueExpose: number | undefined;
  nbVuePossible: number | undefined;
  sensibilite: number | undefined;
}

export class TypeFilm {
  id: number | undefined;
  nom: string | undefined;
}

export class StatutFilm {
  id: number | undefined;
  nom: string | undefined;
}

export class TailleFilm {
  id: number | undefined;
  nom: string | undefined;
  taille: string | undefined;
  formatFilm: FormatFilm | undefined;
}

export class FormatFilm {
  id: number | undefined;
  nom: string | undefined;
}

export class TailleVue {
  id: number | undefined;
  nom: string | undefined;
}
