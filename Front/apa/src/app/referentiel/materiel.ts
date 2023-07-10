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
 export class TypeMateriel{
  id: number | undefined;
  nom: string | undefined;
 }

export class SousType{
  id: number | undefined;
  nom: string | undefined;
}
export class StatutMateriel{
  id: number | undefined;
  nom: string | undefined;
}
export class Marque{
  id: number | undefined;
  nom: string | undefined;
}

export class Modele{
  id: number | undefined;
  nom: string | undefined;
  marque: Marque | undefined;
}
