import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { ContenedorComponent } from './contenedor.component';
import { ContenedorDetailComponent } from './contenedor-detail.component';
import { ContenedorUpdateComponent } from './contenedor-update.component';
import { ContenedorDeleteDialogComponent } from './contenedor-delete-dialog.component';
import { contenedorRoute } from './contenedor.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(contenedorRoute)],
  declarations: [ContenedorComponent, ContenedorDetailComponent, ContenedorUpdateComponent, ContenedorDeleteDialogComponent],
  entryComponents: [ContenedorDeleteDialogComponent],
})
export class LabprovidenciaContenedorModule {}
