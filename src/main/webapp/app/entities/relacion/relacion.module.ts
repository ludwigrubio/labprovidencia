import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { RelacionComponent } from './relacion.component';
import { RelacionDetailComponent } from './relacion-detail.component';
import { RelacionUpdateComponent } from './relacion-update.component';
import { RelacionDeleteDialogComponent } from './relacion-delete-dialog.component';
import { relacionRoute } from './relacion.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(relacionRoute)],
  declarations: [RelacionComponent, RelacionDetailComponent, RelacionUpdateComponent, RelacionDeleteDialogComponent],
  entryComponents: [RelacionDeleteDialogComponent],
})
export class LabprovidenciaRelacionModule {}
