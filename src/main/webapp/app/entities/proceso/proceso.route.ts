import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProceso, Proceso } from 'app/shared/model/proceso.model';
import { ProcesoService } from './proceso.service';
import { ProcesoComponent } from './proceso.component';
import { ProcesoDetailComponent } from './proceso-detail.component';
import { ProcesoUpdateComponent } from './proceso-update.component';

@Injectable({ providedIn: 'root' })
export class ProcesoResolve implements Resolve<IProceso> {
  constructor(private service: ProcesoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProceso> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((proceso: HttpResponse<Proceso>) => {
          if (proceso.body) {
            return of(proceso.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Proceso());
  }
}

export const procesoRoute: Routes = [
  {
    path: '',
    component: ProcesoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'labprovidenciaApp.proceso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProcesoDetailComponent,
    resolve: {
      proceso: ProcesoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.proceso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProcesoUpdateComponent,
    resolve: {
      proceso: ProcesoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.proceso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProcesoUpdateComponent,
    resolve: {
      proceso: ProcesoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.proceso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
