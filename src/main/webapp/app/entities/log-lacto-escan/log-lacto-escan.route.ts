import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILogLactoEscan, LogLactoEscan } from 'app/shared/model/log-lacto-escan.model';
import { LogLactoEscanService } from './log-lacto-escan.service';
import { LogLactoEscanComponent } from './log-lacto-escan.component';
import { LogLactoEscanDetailComponent } from './log-lacto-escan-detail.component';
import { LogLactoEscanUpdateComponent } from './log-lacto-escan-update.component';

@Injectable({ providedIn: 'root' })
export class LogLactoEscanResolve implements Resolve<ILogLactoEscan> {
  constructor(private service: LogLactoEscanService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILogLactoEscan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((logLactoEscan: HttpResponse<LogLactoEscan>) => {
          if (logLactoEscan.body) {
            return of(logLactoEscan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LogLactoEscan());
  }
}

export const logLactoEscanRoute: Routes = [
  {
    path: '',
    component: LogLactoEscanComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'fecha,asc',
      pageTitle: 'labprovidenciaApp.logLactoEscan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LogLactoEscanDetailComponent,
    resolve: {
      logLactoEscan: LogLactoEscanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.logLactoEscan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LogLactoEscanUpdateComponent,
    resolve: {
      logLactoEscan: LogLactoEscanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.logLactoEscan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LogLactoEscanUpdateComponent,
    resolve: {
      logLactoEscan: LogLactoEscanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.logLactoEscan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
