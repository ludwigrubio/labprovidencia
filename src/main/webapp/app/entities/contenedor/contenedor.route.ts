import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContenedor, Contenedor } from 'app/shared/model/contenedor.model';
import { ContenedorService } from './contenedor.service';
import { ContenedorComponent } from './contenedor.component';
import { ContenedorDetailComponent } from './contenedor-detail.component';
import { ContenedorUpdateComponent } from './contenedor-update.component';

@Injectable({ providedIn: 'root' })
export class ContenedorResolve implements Resolve<IContenedor> {
  constructor(private service: ContenedorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContenedor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contenedor: HttpResponse<Contenedor>) => {
          if (contenedor.body) {
            return of(contenedor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Contenedor());
  }
}

export const contenedorRoute: Routes = [
  {
    path: '',
    component: ContenedorComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'labprovidenciaApp.contenedor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContenedorDetailComponent,
    resolve: {
      contenedor: ContenedorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.contenedor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContenedorUpdateComponent,
    resolve: {
      contenedor: ContenedorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.contenedor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContenedorUpdateComponent,
    resolve: {
      contenedor: ContenedorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.contenedor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
