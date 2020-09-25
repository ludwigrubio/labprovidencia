import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { PruebaMicroComponent } from './prueba-micro.component';
import { PruebaMicroDetailComponent } from './prueba-micro-detail.component';
import { PruebaMicroUpdateComponent } from './prueba-micro-update.component';
import { PruebaMicroDeleteDialogComponent } from './prueba-micro-delete-dialog.component';
import { pruebaMicroRoute } from './prueba-micro.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(pruebaMicroRoute)],
  declarations: [PruebaMicroComponent, PruebaMicroDetailComponent, PruebaMicroUpdateComponent, PruebaMicroDeleteDialogComponent],
  entryComponents: [PruebaMicroDeleteDialogComponent],
})
export class LabprovidenciaPruebaMicroModule {}
