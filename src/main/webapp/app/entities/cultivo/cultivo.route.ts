import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICultivo, Cultivo } from 'app/shared/model/cultivo.model';
import { CultivoService } from './cultivo.service';
import { CultivoComponent } from './cultivo.component';
import { CultivoDetailComponent } from './cultivo-detail.component';
import { CultivoUpdateComponent } from './cultivo-update.component';

@Injectable({ providedIn: 'root' })
export class CultivoResolve implements Resolve<ICultivo> {
  constructor(private service: CultivoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICultivo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cultivo: HttpResponse<Cultivo>) => {
          if (cultivo.body) {
            return of(cultivo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Cultivo());
  }
}

export const cultivoRoute: Routes = [
  {
    path: '',
    component: CultivoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'labprovidenciaApp.cultivo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CultivoDetailComponent,
    resolve: {
      cultivo: CultivoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.cultivo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CultivoUpdateComponent,
    resolve: {
      cultivo: CultivoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.cultivo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CultivoUpdateComponent,
    resolve: {
      cultivo: CultivoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.cultivo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
