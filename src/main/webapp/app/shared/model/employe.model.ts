import { IProjet } from 'app/shared/model/projet.model';
import { IBureau } from 'app/shared/model/bureau.model';

export interface IEmploye {
  id?: number;
  nom?: string;
  phone?: string;
  projet?: IProjet;
  bureau?: IBureau;
}

export class Employe implements IEmploye {
  constructor(public id?: number, public nom?: string, public phone?: string, public projet?: IProjet, public bureau?: IBureau) {}
}
