import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRelacion, Relacion } from 'app/shared/model/relacion.model';
import { RelacionService } from './relacion.service';
import { RelacionComponent } from './relacion.component';
import { RelacionDetailComponent } from './relacion-detail.component';
import { RelacionUpdateComponent } from './relacion-update.component';

@Injectable({ providedIn: 'root' })
export class RelacionResolve implements Resolve<IRelacion> {
  constructor(private service: RelacionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRelacion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((relacion: HttpResponse<Relacion>) => {
          if (relacion.body) {
            return of(relacion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Relacion());
  }
}

export const relacionRoute: Routes = [
  {
    path: '',
    component: RelacionComponent,
    data: {
      authorities: [Authority.ADMIN, Authority.EDITOR],
      defaultSort: 'id,asc',
      pageTitle: 'labprovidenciaApp.relacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RelacionDetailComponent,
    resolve: {
      relacion: RelacionResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.EDITOR],
      pageTitle: 'labprovidenciaApp.relacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RelacionUpdateComponent,
    resolve: {
      relacion: RelacionResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.EDITOR],
      pageTitle: 'labprovidenciaApp.relacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RelacionUpdateComponent,
    resolve: {
      relacion: RelacionResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.EDITOR],
      pageTitle: 'labprovidenciaApp.relacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
