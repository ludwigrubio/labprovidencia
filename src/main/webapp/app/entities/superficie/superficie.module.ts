import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { SuperficieComponent } from './superficie.component';
import { SuperficieDetailComponent } from './superficie-detail.component';
import { SuperficieUpdateComponent } from './superficie-update.component';
import { SuperficieDeleteDialogComponent } from './superficie-delete-dialog.component';
import { superficieRoute } from './superficie.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(superficieRoute)],
  declarations: [SuperficieComponent, SuperficieDetailComponent, SuperficieUpdateComponent, SuperficieDeleteDialogComponent],
  entryComponents: [SuperficieDeleteDialogComponent],
})
export class LabprovidenciaSuperficieModule {}
