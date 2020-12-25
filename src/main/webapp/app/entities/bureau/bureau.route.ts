import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBureau, Bureau } from 'app/shared/model/bureau.model';
import { BureauService } from './bureau.service';
import { BureauComponent } from './bureau.component';
import { BureauDetailComponent } from './bureau-detail.component';
import { BureauUpdateComponent } from './bureau-update.component';

@Injectable({ providedIn: 'root' })
export class BureauResolve implements Resolve<IBureau> {
  constructor(private service: BureauService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBureau> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bureau: HttpResponse<Bureau>) => {
          if (bureau.body) {
            return of(bureau.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bureau());
  }
}

export const bureauRoute: Routes = [
  {
    path: '',
    component: BureauComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'projetSoaApp.bureau.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BureauDetailComponent,
    resolve: {
      bureau: BureauResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'projetSoaApp.bureau.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BureauUpdateComponent,
    resolve: {
      bureau: BureauResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'projetSoaApp.bureau.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BureauUpdateComponent,
    resolve: {
      bureau: BureauResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'projetSoaApp.bureau.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
