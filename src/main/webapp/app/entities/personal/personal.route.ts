import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPersonal, Personal } from 'app/shared/model/personal.model';
import { PersonalService } from './personal.service';
import { PersonalComponent } from './personal.component';
import { PersonalDetailComponent } from './personal-detail.component';
import { PersonalUpdateComponent } from './personal-update.component';

@Injectable({ providedIn: 'root' })
export class PersonalResolve implements Resolve<IPersonal> {
  constructor(private service: PersonalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((personal: HttpResponse<Personal>) => {
          if (personal.body) {
            return of(personal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Personal());
  }
}

export const personalRoute: Routes = [
  {
    path: '',
    component: PersonalComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'labprovidenciaApp.personal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonalDetailComponent,
    resolve: {
      personal: PersonalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.personal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonalUpdateComponent,
    resolve: {
      personal: PersonalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.personal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonalUpdateComponent,
    resolve: {
      personal: PersonalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'labprovidenciaApp.personal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
