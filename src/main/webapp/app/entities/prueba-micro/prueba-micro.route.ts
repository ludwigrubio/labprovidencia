import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPruebaMicro, PruebaMicro } from 'app/shared/model/prueba-micro.model';
import { PruebaMicroService } from './prueba-micro.service';
import { PruebaMicroComponent } from './prueba-micro.component';
import { PruebaMicroDetailComponent } from './prueba-micro-detail.component';
import { PruebaMicroUpdateComponent } from './prueba-micro-update.component';

@Injectable({ providedIn: 'root' })
export class PruebaMicroResolve implements Resolve<IPruebaMicro> {
  constructor(private service: PruebaMicroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPruebaMicro> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pruebaMicro: HttpResponse<PruebaMicro>) => {
          if (pruebaMicro.body) {
            return of(pruebaMicro.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PruebaMicro());
  }
}

export const pruebaMicroRoute: Routes = [
  {
    path: '',
    component: PruebaMicroComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,desc',
      pageTitle: 'labprovidenciaApp.pruebaMicro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PruebaMicroDetailComponent,
    resolve: {
      pruebaMicro: PruebaMicroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.pruebaMicro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PruebaMicroUpdateComponent,
    resolve: {
      pruebaMicro: PruebaMicroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.pruebaMicro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PruebaMicroUpdateComponent,
    resolve: {
      pruebaMicro: PruebaMicroResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.EDITOR],
      pageTitle: 'labprovidenciaApp.pruebaMicro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
