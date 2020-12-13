import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { LogLactoEscanComponent } from './log-lacto-escan.component';
import { LogLactoEscanDetailComponent } from './log-lacto-escan-detail.component';
import { LogLactoEscanUpdateComponent } from './log-lacto-escan-update.component';
import { LogLactoEscanDeleteDialogComponent } from './log-lacto-escan-delete-dialog.component';
import { logLactoEscanRoute } from './log-lacto-escan.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(logLactoEscanRoute)],
  declarations: [LogLactoEscanComponent, LogLactoEscanDetailComponent, LogLactoEscanUpdateComponent, LogLactoEscanDeleteDialogComponent],
  entryComponents: [LogLactoEscanDeleteDialogComponent],
})
export class LabprovidenciaLogLactoEscanModule {}
