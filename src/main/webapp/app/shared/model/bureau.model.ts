import { IEmploye } from 'app/shared/model/employe.model';

export interface IBureau {
  id?: number;
  numero?: number;
  employe?: IEmploye;
}

export class Bureau implements IBureau {
  constructor(public id?: number, public numero?: number, public employe?: IEmploye) {}
}
