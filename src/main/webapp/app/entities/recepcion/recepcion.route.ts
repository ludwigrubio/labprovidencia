import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRecepcion, Recepcion } from 'app/shared/model/recepcion.model';
import { RecepcionService } from './recepcion.service';
import { RecepcionComponent } from './recepcion.component';
import { RecepcionDetailComponent } from './recepcion-detail.component';
import { RecepcionUpdateComponent } from './recepcion-update.component';

@Injectable({ providedIn: 'root' })
export class RecepcionResolve implements Resolve<IRecepcion> {
  constructor(private service: RecepcionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRecepcion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((recepcion: HttpResponse<Recepcion>) => {
          if (recepcion.body) {
            return of(recepcion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Recepcion());
  }
}

export const recepcionRoute: Routes = [
  {
    path: '',
    component: RecepcionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'labprovidenciaApp.recepcion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RecepcionDetailComponent,
    resolve: {
      recepcion: RecepcionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.recepcion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RecepcionUpdateComponent,
    resolve: {
      recepcion: RecepcionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.recepcion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RecepcionUpdateComponent,
    resolve: {
      recepcion: RecepcionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.recepcion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
