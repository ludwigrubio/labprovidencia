import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { FQMantequillaComponent } from './fq-mantequilla.component';
import { FQMantequillaDetailComponent } from './fq-mantequilla-detail.component';
import { FQMantequillaUpdateComponent } from './fq-mantequilla-update.component';
import { FQMantequillaDeleteDialogComponent } from './fq-mantequilla-delete-dialog.component';
import { fQMantequillaRoute } from './fq-mantequilla.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(fQMantequillaRoute)],
  declarations: [FQMantequillaComponent, FQMantequillaDetailComponent, FQMantequillaUpdateComponent, FQMantequillaDeleteDialogComponent],
  entryComponents: [FQMantequillaDeleteDialogComponent],
})
export class LabprovidenciaFQMantequillaModule {}
