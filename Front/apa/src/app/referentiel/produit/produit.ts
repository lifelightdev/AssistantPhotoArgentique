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

class Film {
  id: number | undefined;
  statutFilm: StatutFilm | undefined;
  tailleFilm: TailleFilm | undefined;
  typeFilm: TypeFilm | undefined;
}

class TypeFilm {
  id: number | undefined;
  nom: string | undefined;
}

class StatutFilm {
  id: number | undefined;
  nom: string | undefined;
}

class TailleFilm {
  id: number | undefined;
  nom: string | undefined;
  taille: string | undefined;
  formatFilm: FormatFilm | undefined;
}

class FormatFilm {
  id: number | undefined;
  nom: string | undefined;
}
