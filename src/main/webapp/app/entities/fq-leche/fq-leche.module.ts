import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { FQLecheComponent } from './fq-leche.component';
import { FQLecheDetailComponent } from './fq-leche-detail.component';
import { FQLecheUpdateComponent } from './fq-leche-update.component';
import { FQLecheDeleteDialogComponent } from './fq-leche-delete-dialog.component';
import { fQLecheRoute } from './fq-leche.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(fQLecheRoute)],
  declarations: [FQLecheComponent, FQLecheDetailComponent, FQLecheUpdateComponent, FQLecheDeleteDialogComponent],
  entryComponents: [FQLecheDeleteDialogComponent],
})
export class LabprovidenciaFQLecheModule {}
