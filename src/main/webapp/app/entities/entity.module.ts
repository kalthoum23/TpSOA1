import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'competence',
        loadChildren: () => import('./competence/competence.module').then(m => m.ProjetSoaCompetenceModule),
      },
      {
        path: 'projet',
        loadChildren: () => import('./projet/projet.module').then(m => m.ProjetSoaProjetModule),
      },
      {
        path: 'employe',
        loadChildren: () => import('./employe/employe.module').then(m => m.ProjetSoaEmployeModule),
      },
      {
        path: 'bureau',
        loadChildren: () => import('./bureau/bureau.module').then(m => m.ProjetSoaBureauModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ProjetSoaEntityModule {}
