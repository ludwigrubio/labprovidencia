import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { CultivoComponent } from './cultivo.component';
import { CultivoDetailComponent } from './cultivo-detail.component';
import { CultivoUpdateComponent } from './cultivo-update.component';
import { CultivoDeleteDialogComponent } from './cultivo-delete-dialog.component';
import { cultivoRoute } from './cultivo.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(cultivoRoute)],
  declarations: [CultivoComponent, CultivoDetailComponent, CultivoUpdateComponent, CultivoDeleteDialogComponent],
  entryComponents: [CultivoDeleteDialogComponent],
})
export class LabprovidenciaCultivoModule {}
