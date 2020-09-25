import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFQSuero, FQSuero } from 'app/shared/model/fq-suero.model';
import { FQSueroService } from './fq-suero.service';
import { FQSueroComponent } from './fq-suero.component';
import { FQSueroDetailComponent } from './fq-suero-detail.component';
import { FQSueroUpdateComponent } from './fq-suero-update.component';

@Injectable({ providedIn: 'root' })
export class FQSueroResolve implements Resolve<IFQSuero> {
  constructor(private service: FQSueroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFQSuero> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fQSuero: HttpResponse<FQSuero>) => {
          if (fQSuero.body) {
            return of(fQSuero.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FQSuero());
  }
}

export const fQSueroRoute: Routes = [
  {
    path: '',
    component: FQSueroComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'labprovidenciaApp.fQSuero.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FQSueroDetailComponent,
    resolve: {
      fQSuero: FQSueroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.fQSuero.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FQSueroUpdateComponent,
    resolve: {
      fQSuero: FQSueroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.fQSuero.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FQSueroUpdateComponent,
    resolve: {
      fQSuero: FQSueroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.fQSuero.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
