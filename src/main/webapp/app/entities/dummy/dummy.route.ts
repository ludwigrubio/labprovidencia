import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDummy, Dummy } from 'app/shared/model/dummy.model';
import { DummyService } from './dummy.service';
import { DummyComponent } from './dummy.component';
import { DummyDetailComponent } from './dummy-detail.component';
import { DummyUpdateComponent } from './dummy-update.component';

@Injectable({ providedIn: 'root' })
export class DummyResolve implements Resolve<IDummy> {
  constructor(private service: DummyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDummy> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dummy: HttpResponse<Dummy>) => {
          if (dummy.body) {
            return of(dummy.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dummy());
  }
}

export const dummyRoute: Routes = [
  {
    path: '',
    component: DummyComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'labprovidenciaApp.dummy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DummyDetailComponent,
    resolve: {
      dummy: DummyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.dummy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DummyUpdateComponent,
    resolve: {
      dummy: DummyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.dummy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DummyUpdateComponent,
    resolve: {
      dummy: DummyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.dummy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
