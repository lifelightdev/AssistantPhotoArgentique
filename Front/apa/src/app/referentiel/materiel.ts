export class Materiel {
  id: number | undefined;
  name: string | undefined;
  type: Type | undefined;
  sousType: SousType | undefined;
  status: Status | undefined;
  marque: Marque | undefined;
  modele: String | undefined;
  photo: undefined;
  modeEmploie: undefined;
  remarque: string | undefined;
}
 export class Type{
  id: number | undefined;
  nom: string | undefined;
 }

export class SousType{
  id: number | undefined;
  nom: string | undefined;
}
export class Status{
  id: number | undefined;
  nom: string | undefined;
}
export class Marque{
  id: number | undefined;
  nom: string | undefined;
}
