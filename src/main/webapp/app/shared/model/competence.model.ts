export interface ICompetence {
  id?: number;
}

export class Competence implements ICompetence {
  constructor(public id?: number) {}
}
