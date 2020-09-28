import { Route } from '@angular/router';

import { RegisterComponent } from './register.component';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

export const registerRoute: Route = {
  path: 'register',
  component: RegisterComponent,
  data: {
    authorities: [Authority.ADMIN],
    pageTitle: 'register.title',
  },
  canActivate: [UserRouteAccessService],
};
