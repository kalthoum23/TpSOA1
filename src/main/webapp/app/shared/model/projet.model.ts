import { IEmploye } from 'app/shared/model/employe.model';

export interface IProjet {
  id?: number;
  intitule?: string;
  employes?: IEmploye[];
}

export class Projet implements IProjet {
  constructor(public id?: number, public intitule?: string, public employes?: IEmploye[]) {}
}
