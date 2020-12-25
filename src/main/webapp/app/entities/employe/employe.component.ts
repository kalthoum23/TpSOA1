import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmploye } from 'app/shared/model/employe.model';
import { EmployeService } from './employe.service';
import { EmployeDeleteDialogComponent } from './employe-delete-dialog.component';

@Component({
  selector: 'jhi-employe',
  templateUrl: './employe.component.html',
})
export class EmployeComponent implements OnInit, OnDestroy {
  employes?: IEmploye[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected employeService: EmployeService,
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
      this.employeService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IEmploye[]>) => (this.employes = res.body || []));
      return;
    }

    this.employeService.query().subscribe((res: HttpResponse<IEmploye[]>) => (this.employes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmployes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmploye): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmployes(): void {
    this.eventSubscriber = this.eventManager.subscribe('employeListModification', () => this.loadAll());
  }

  delete(employe: IEmploye): void {
    const modalRef = this.modalService.open(EmployeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.employe = employe;
  }
}
