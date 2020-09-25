import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { FQCremaComponent } from './fq-crema.component';
import { FQCremaDetailComponent } from './fq-crema-detail.component';
import { FQCremaUpdateComponent } from './fq-crema-update.component';
import { FQCremaDeleteDialogComponent } from './fq-crema-delete-dialog.component';
import { fQCremaRoute } from './fq-crema.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(fQCremaRoute)],
  declarations: [FQCremaComponent, FQCremaDetailComponent, FQCremaUpdateComponent, FQCremaDeleteDialogComponent],
  entryComponents: [FQCremaDeleteDialogComponent],
})
export class LabprovidenciaFQCremaModule {}
