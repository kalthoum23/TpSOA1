import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBureau } from 'app/shared/model/bureau.model';
import { BureauService } from './bureau.service';

@Component({
  templateUrl: './bureau-delete-dialog.component.html',
})
export class BureauDeleteDialogComponent {
  bureau?: IBureau;

  constructor(protected bureauService: BureauService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bureauService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bureauListModification');
      this.activeModal.close();
    });
  }
}
