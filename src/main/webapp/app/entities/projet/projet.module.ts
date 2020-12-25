import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetSoaSharedModule } from 'app/shared/shared.module';
import { ProjetComponent } from './projet.component';
import { ProjetDetailComponent } from './projet-detail.component';
import { ProjetUpdateComponent } from './projet-update.component';
import { ProjetDeleteDialogComponent } from './projet-delete-dialog.component';
import { projetRoute } from './projet.route';

@NgModule({
  imports: [ProjetSoaSharedModule, RouterModule.forChild(projetRoute)],
  declarations: [ProjetComponent, ProjetDetailComponent, ProjetUpdateComponent, ProjetDeleteDialogComponent],
  entryComponents: [ProjetDeleteDialogComponent],
})
export class ProjetSoaProjetModule {}
