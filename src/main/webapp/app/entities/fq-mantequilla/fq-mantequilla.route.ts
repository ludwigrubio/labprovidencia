import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFQMantequilla, FQMantequilla } from 'app/shared/model/fq-mantequilla.model';
import { FQMantequillaService } from './fq-mantequilla.service';
import { FQMantequillaComponent } from './fq-mantequilla.component';
import { FQMantequillaDetailComponent } from './fq-mantequilla-detail.component';
import { FQMantequillaUpdateComponent } from './fq-mantequilla-update.component';

@Injectable({ providedIn: 'root' })
export class FQMantequillaResolve implements Resolve<IFQMantequilla> {
  constructor(private service: FQMantequillaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFQMantequilla> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fQMantequilla: HttpResponse<FQMantequilla>) => {
          if (fQMantequilla.body) {
            return of(fQMantequilla.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FQMantequilla());
  }
}

export const fQMantequillaRoute: Routes = [
  {
    path: '',
    component: FQMantequillaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'fecha,desc',
      pageTitle: 'labprovidenciaApp.fQMantequilla.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FQMantequillaDetailComponent,
    resolve: {
      fQMantequilla: FQMantequillaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.fQMantequilla.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FQMantequillaUpdateComponent,
    resolve: {
      fQMantequilla: FQMantequillaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.fQMantequilla.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FQMantequillaUpdateComponent,
    resolve: {
      fQMantequilla: FQMantequillaResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.EDITOR],
      pageTitle: 'labprovidenciaApp.fQMantequilla.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
