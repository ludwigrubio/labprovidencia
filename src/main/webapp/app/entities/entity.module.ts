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
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class LabprovidenciaEntityModule {}
