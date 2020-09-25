import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { FQQuesoComponent } from './fq-queso.component';
import { FQQuesoDetailComponent } from './fq-queso-detail.component';
import { FQQuesoUpdateComponent } from './fq-queso-update.component';
import { FQQuesoDeleteDialogComponent } from './fq-queso-delete-dialog.component';
import { fQQuesoRoute } from './fq-queso.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(fQQuesoRoute)],
  declarations: [FQQuesoComponent, FQQuesoDetailComponent, FQQuesoUpdateComponent, FQQuesoDeleteDialogComponent],
  entryComponents: [FQQuesoDeleteDialogComponent],
})
export class LabprovidenciaFQQuesoModule {}
