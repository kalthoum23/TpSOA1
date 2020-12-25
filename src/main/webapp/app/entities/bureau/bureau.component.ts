import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBureau } from 'app/shared/model/bureau.model';
import { BureauService } from './bureau.service';
import { BureauDeleteDialogComponent } from './bureau-delete-dialog.component';

@Component({
  selector: 'jhi-bureau',
  templateUrl: './bureau.component.html',
})
export class BureauComponent implements OnInit, OnDestroy {
  bureaus?: IBureau[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected bureauService: BureauService,
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
      this.bureauService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IBureau[]>) => (this.bureaus = res.body || []));
      return;
    }

    this.bureauService.query().subscribe((res: HttpResponse<IBureau[]>) => (this.bureaus = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBureaus();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBureau): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBureaus(): void {
    this.eventSubscriber = this.eventManager.subscribe('bureauListModification', () => this.loadAll());
  }

  delete(bureau: IBureau): void {
    const modalRef = this.modalService.open(BureauDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bureau = bureau;
  }
}
