import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { RecepcionComponent } from './recepcion.component';
import { RecepcionDetailComponent } from './recepcion-detail.component';
import { RecepcionUpdateComponent } from './recepcion-update.component';
import { RecepcionDeleteDialogComponent } from './recepcion-delete-dialog.component';
import { recepcionRoute } from './recepcion.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(recepcionRoute)],
  declarations: [RecepcionComponent, RecepcionDetailComponent, RecepcionUpdateComponent, RecepcionDeleteDialogComponent],
  entryComponents: [RecepcionDeleteDialogComponent],
})
export class LabprovidenciaRecepcionModule {}
