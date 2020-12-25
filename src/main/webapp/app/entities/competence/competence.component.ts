import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompetence } from 'app/shared/model/competence.model';
import { CompetenceService } from './competence.service';
import { CompetenceDeleteDialogComponent } from './competence-delete-dialog.component';

@Component({
  selector: 'jhi-competence',
  templateUrl: './competence.component.html',
})
export class CompetenceComponent implements OnInit, OnDestroy {
  competences?: ICompetence[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected competenceService: CompetenceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.competenceService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ICompetence[]>) => (this.competences = res.body || []));
      return;
    }

    this.competenceService.query().subscribe((res: HttpResponse<ICompetence[]>) => (this.competences = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCompetences();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICompetence): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCompetences(): void {
    this.eventSubscriber = this.eventManager.subscribe('competenceListModification', () => this.loadAll());
  }

  delete(competence: ICompetence): void {
    const modalRef = this.modalService.open(CompetenceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.competence = competence;
  }
}
