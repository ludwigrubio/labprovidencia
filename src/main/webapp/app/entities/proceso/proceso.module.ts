import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { ProcesoComponent } from './proceso.component';
import { ProcesoDetailComponent } from './proceso-detail.component';
import { ProcesoUpdateComponent } from './proceso-update.component';
import { ProcesoDeleteDialogComponent } from './proceso-delete-dialog.component';
import { procesoRoute } from './proceso.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(procesoRoute)],
  declarations: [ProcesoComponent, ProcesoDetailComponent, ProcesoUpdateComponent, ProcesoDeleteDialogComponent],
  entryComponents: [ProcesoDeleteDialogComponent],
})
export class LabprovidenciaProcesoModule {}
