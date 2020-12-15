import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFQQueso, FQQueso } from 'app/shared/model/fq-queso.model';
import { FQQuesoService } from './fq-queso.service';
import { FQQuesoComponent } from './fq-queso.component';
import { FQQuesoDetailComponent } from './fq-queso-detail.component';
import { FQQuesoUpdateComponent } from './fq-queso-update.component';

@Injectable({ providedIn: 'root' })
export class FQQuesoResolve implements Resolve<IFQQueso> {
  constructor(private service: FQQuesoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFQQueso> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fQQueso: HttpResponse<FQQueso>) => {
          if (fQQueso.body) {
            return of(fQQueso.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FQQueso());
  }
}

export const fQQuesoRoute: Routes = [
  {
    path: '',
    component: FQQuesoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'fecha,desc',
      pageTitle: 'labprovidenciaApp.fQQueso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FQQuesoDetailComponent,
    resolve: {
      fQQueso: FQQuesoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.fQQueso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FQQuesoUpdateComponent,
    resolve: {
      fQQueso: FQQuesoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.fQQueso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FQQuesoUpdateComponent,
    resolve: {
      fQQueso: FQQuesoResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.EDITOR],
      pageTitle: 'labprovidenciaApp.fQQueso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
