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
      {
        path: 'relacion',
        loadChildren: () => import('./relacion/relacion.module').then(m => m.LabprovidenciaRelacionModule),
      },
      {
        path: 'cultivo',
        loadChildren: () => import('./cultivo/cultivo.module').then(m => m.LabprovidenciaCultivoModule),
      },
      {
        path: 'superficie',
        loadChildren: () => import('./superficie/superficie.module').then(m => m.LabprovidenciaSuperficieModule),
      },
      {
        path: 'recepcion',
        loadChildren: () => import('./recepcion/recepcion.module').then(m => m.LabprovidenciaRecepcionModule),
      },
      {
        path: 'tipo-producto',
        loadChildren: () => import('./tipo-producto/tipo-producto.module').then(m => m.LabprovidenciaTipoProductoModule),
      },
      {
        path: 'producto',
        loadChildren: () => import('./producto/producto.module').then(m => m.LabprovidenciaProductoModule),
      },
      {
        path: 'user-extra',
        loadChildren: () => import('./user-extra/user-extra.module').then(m => m.LabprovidenciaUserExtraModule),
      },
      {
        path: 'prueba-micro',
        loadChildren: () => import('./prueba-micro/prueba-micro.module').then(m => m.LabprovidenciaPruebaMicroModule),
      },
      {
        path: 'fq-crema',
        loadChildren: () => import('./fq-crema/fq-crema.module').then(m => m.LabprovidenciaFQCremaModule),
      },
      {
        path: 'fq-leche',
        loadChildren: () => import('./fq-leche/fq-leche.module').then(m => m.LabprovidenciaFQLecheModule),
      },
      {
        path: 'fq-mantequilla',
        loadChildren: () => import('./fq-mantequilla/fq-mantequilla.module').then(m => m.LabprovidenciaFQMantequillaModule),
      },
      {
        path: 'fq-queso',
        loadChildren: () => import('./fq-queso/fq-queso.module').then(m => m.LabprovidenciaFQQuesoModule),
      },
      {
        path: 'fq-suero',
        loadChildren: () => import('./fq-suero/fq-suero.module').then(m => m.LabprovidenciaFQSueroModule),
      },
      {
        path: 'contenedor',
        loadChildren: () => import('./contenedor/contenedor.module').then(m => m.LabprovidenciaContenedorModule),
      },
      {
        path: 'log-lacto-escan',
        loadChildren: () => import('./log-lacto-escan/log-lacto-escan.module').then(m => m.LabprovidenciaLogLactoEscanModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class LabprovidenciaEntityModule {}
