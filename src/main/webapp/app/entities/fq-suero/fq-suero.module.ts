import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { FQSueroComponent } from './fq-suero.component';
import { FQSueroDetailComponent } from './fq-suero-detail.component';
import { FQSueroUpdateComponent } from './fq-suero-update.component';
import { FQSueroDeleteDialogComponent } from './fq-suero-delete-dialog.component';
import { fQSueroRoute } from './fq-suero.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(fQSueroRoute)],
  declarations: [FQSueroComponent, FQSueroDetailComponent, FQSueroUpdateComponent, FQSueroDeleteDialogComponent],
  entryComponents: [FQSueroDeleteDialogComponent],
})
export class LabprovidenciaFQSueroModule {}
