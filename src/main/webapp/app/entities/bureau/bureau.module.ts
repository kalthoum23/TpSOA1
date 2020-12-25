import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetSoaSharedModule } from 'app/shared/shared.module';
import { BureauComponent } from './bureau.component';
import { BureauDetailComponent } from './bureau-detail.component';
import { BureauUpdateComponent } from './bureau-update.component';
import { BureauDeleteDialogComponent } from './bureau-delete-dialog.component';
import { bureauRoute } from './bureau.route';

@NgModule({
  imports: [ProjetSoaSharedModule, RouterModule.forChild(bureauRoute)],
  declarations: [BureauComponent, BureauDetailComponent, BureauUpdateComponent, BureauDeleteDialogComponent],
  entryComponents: [BureauDeleteDialogComponent],
})
export class ProjetSoaBureauModule {}
