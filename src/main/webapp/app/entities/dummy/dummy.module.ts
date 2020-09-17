import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { DummyComponent } from './dummy.component';
import { DummyDetailComponent } from './dummy-detail.component';
import { DummyUpdateComponent } from './dummy-update.component';
import { DummyDeleteDialogComponent } from './dummy-delete-dialog.component';
import { dummyRoute } from './dummy.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(dummyRoute)],
  declarations: [DummyComponent, DummyDetailComponent, DummyUpdateComponent, DummyDeleteDialogComponent],
  entryComponents: [DummyDeleteDialogComponent],
})
export class LabprovidenciaDummyModule {}
