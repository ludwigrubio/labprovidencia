import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'personal',
        loadChildren: () => import('./personal/personal.module').then(m => m.LabprovidenciaPersonalModule),
      },
      {
        path: 'area',
        loadChildren: () => import('./area/area.module').then(m => m.LabprovidenciaAreaModule),
      },
      {
        path: 'dummy',
        loadChildren: () => import('./dummy/dummy.module').then(m => m.LabprovidenciaDummyModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class LabprovidenciaEntityModule {}
