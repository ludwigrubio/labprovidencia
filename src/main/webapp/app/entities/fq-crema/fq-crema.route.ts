import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFQCrema, FQCrema } from 'app/shared/model/fq-crema.model';
import { FQCremaService } from './fq-crema.service';
import { FQCremaComponent } from './fq-crema.component';
import { FQCremaDetailComponent } from './fq-crema-detail.component';
import { FQCremaUpdateComponent } from './fq-crema-update.component';

@Injectable({ providedIn: 'root' })
export class FQCremaResolve implements Resolve<IFQCrema> {
  constructor(private service: FQCremaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFQCrema> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fQCrema: HttpResponse<FQCrema>) => {
          if (fQCrema.body) {
            return of(fQCrema.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FQCrema());
  }
}

export const fQCremaRoute: Routes = [
  {
    path: '',
    component: FQCremaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'fecha,desc',
      pageTitle: 'labprovidenciaApp.fQCrema.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FQCremaDetailComponent,
    resolve: {
      fQCrema: FQCremaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.fQCrema.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FQCremaUpdateComponent,
    resolve: {
      fQCrema: FQCremaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.fQCrema.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FQCremaUpdateComponent,
    resolve: {
      fQCrema: FQCremaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.fQCrema.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
