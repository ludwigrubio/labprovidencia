import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoProducto, TipoProducto } from 'app/shared/model/tipo-producto.model';
import { TipoProductoService } from './tipo-producto.service';
import { TipoProductoComponent } from './tipo-producto.component';
import { TipoProductoDetailComponent } from './tipo-producto-detail.component';
import { TipoProductoUpdateComponent } from './tipo-producto-update.component';

@Injectable({ providedIn: 'root' })
export class TipoProductoResolve implements Resolve<ITipoProducto> {
  constructor(private service: TipoProductoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoProducto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoProducto: HttpResponse<TipoProducto>) => {
          if (tipoProducto.body) {
            return of(tipoProducto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoProducto());
  }
}

export const tipoProductoRoute: Routes = [
  {
    path: '',
    component: TipoProductoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'labprovidenciaApp.tipoProducto.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoProductoDetailComponent,
    resolve: {
      tipoProducto: TipoProductoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.tipoProducto.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoProductoUpdateComponent,
    resolve: {
      tipoProducto: TipoProductoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.tipoProducto.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoProductoUpdateComponent,
    resolve: {
      tipoProducto: TipoProductoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.tipoProducto.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
