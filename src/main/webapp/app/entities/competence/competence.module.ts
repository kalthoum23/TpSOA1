import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetSoaSharedModule } from 'app/shared/shared.module';
import { CompetenceComponent } from './competence.component';
import { CompetenceDetailComponent } from './competence-detail.component';
import { CompetenceUpdateComponent } from './competence-update.component';
import { CompetenceDeleteDialogComponent } from './competence-delete-dialog.component';
import { competenceRoute } from './competence.route';

@NgModule({
  imports: [ProjetSoaSharedModule, RouterModule.forChild(competenceRoute)],
  declarations: [CompetenceComponent, CompetenceDetailComponent, CompetenceUpdateComponent, CompetenceDeleteDialogComponent],
  entryComponents: [CompetenceDeleteDialogComponent],
})
export class ProjetSoaCompetenceModule {}
