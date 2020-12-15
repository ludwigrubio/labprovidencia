import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFQLeche, FQLeche } from 'app/shared/model/fq-leche.model';
import { FQLecheService } from './fq-leche.service';
import { FQLecheComponent } from './fq-leche.component';
import { FQLecheDetailComponent } from './fq-leche-detail.component';
import { FQLecheUpdateComponent } from './fq-leche-update.component';

@Injectable({ providedIn: 'root' })
export class FQLecheResolve implements Resolve<IFQLeche> {
  constructor(private service: FQLecheService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFQLeche> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fQLeche: HttpResponse<FQLeche>) => {
          if (fQLeche.body) {
            return of(fQLeche.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FQLeche());
  }
}

export const fQLecheRoute: Routes = [
  {
    path: '',
    component: FQLecheComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'fecha,desc',
      pageTitle: 'labprovidenciaApp.fQLeche.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FQLecheDetailComponent,
    resolve: {
      fQLeche: FQLecheResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.fQLeche.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FQLecheUpdateComponent,
    resolve: {
      fQLeche: FQLecheResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.fQLeche.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FQLecheUpdateComponent,
    resolve: {
      fQLeche: FQLecheResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.fQLeche.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
