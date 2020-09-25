import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISuperficie, Superficie } from 'app/shared/model/superficie.model';
import { SuperficieService } from './superficie.service';
import { SuperficieComponent } from './superficie.component';
import { SuperficieDetailComponent } from './superficie-detail.component';
import { SuperficieUpdateComponent } from './superficie-update.component';

@Injectable({ providedIn: 'root' })
export class SuperficieResolve implements Resolve<ISuperficie> {
  constructor(private service: SuperficieService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISuperficie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((superficie: HttpResponse<Superficie>) => {
          if (superficie.body) {
            return of(superficie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Superficie());
  }
}

export const superficieRoute: Routes = [
  {
    path: '',
    component: SuperficieComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'labprovidenciaApp.superficie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SuperficieDetailComponent,
    resolve: {
      superficie: SuperficieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.superficie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SuperficieUpdateComponent,
    resolve: {
      superficie: SuperficieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.superficie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SuperficieUpdateComponent,
    resolve: {
      superficie: SuperficieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.superficie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
