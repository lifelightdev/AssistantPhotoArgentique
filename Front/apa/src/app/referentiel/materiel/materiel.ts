import {Produit} from "../produit/produit";

export class Materiel {
  id: number | undefined;
  nom: string | undefined;
  typeMateriel: TypeMateriel | undefined;
  sousType: SousType | undefined;
  statutMateriel: StatutMateriel | undefined;
  modele: Modele | undefined;
  photo: undefined;
  modeEmploie: undefined;
  remarque: string | undefined;
}

export class TypeMateriel {
  id: number | undefined;
  nom: string | undefined;
}

export class SousType {
  id: number | undefined;
  nom: string | undefined;
}

export class StatutMateriel {
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

export class ModelRechercheMateriel {
  nom: string | undefined;
  marque: Marque | undefined;
  modele: Modele | undefined;
  remarque: string | undefined;
  sousType: SousType | undefined;
  statutMateriel: StatutMateriel | undefined;
  typeMateriel: TypeMateriel | undefined;
}

export class AppareilPhoto {
  id: number | undefined;
  materiel: Materiel | undefined;
  typeAppareilPhoto: TypeAppareilPhoto | undefined;
  typeFixationObjectif: TypeFixation | undefined;
  objectif: Objectif | undefined;
  typeFixationFlash: TypeFixation | undefined;
  typeFixationPied: TypeFixation | undefined;
  typeFixationFiltre: TypeFixation | undefined;
  chassis: Chassis | undefined;
  // @ts-ignore
  film: Produit.Film | undefined;
  typeMiseAuPoint: TypeMiseAuPoint | undefined;
}

class TypeAppareilPhoto {
  id: number | undefined;
  nom: string | undefined;
}

class TypeFixation {
  id: number | undefined;
  nom: string | undefined;
  sousTypeMateriel: SousTypeMateriel | undefined;
}

class SousTypeMateriel {
  id: number | undefined;
  nom: string | undefined;
  type: TypeMateriel | undefined;
}

class Objectif {
  id: number | undefined;
  materiel: Materiel | undefined;
  typeFixationObjectif: TypeFixation | undefined;
  typeFixationFlash: TypeFixation | undefined;
  typeFixationPied: TypeFixation | undefined;
  typeFixationFiltre: TypeFixation | undefined;
}

class Focal {
  id: number | undefined;
  nom: string | undefined;
}

class TypeMiseAuPoint {
  id: number | undefined;
  nom: string | undefined;
}

class Chassis {
  id: number | undefined;
  materiel: Materiel | undefined;
  statutChassis: StatutChassis | undefined;
  dimensionChassis: DimensionChassis | undefined;
  // @ts-ignore
  film: Produit.Film | undefined;
}

class StatutChassis {
  id: number | undefined;
  nom: string | undefined;
}

class DimensionChassis {
  id: number | undefined;
  nom: string | undefined;
}
