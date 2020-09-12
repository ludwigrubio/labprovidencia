import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { PersonalComponent } from './personal.component';
import { PersonalDetailComponent } from './personal-detail.component';
import { PersonalUpdateComponent } from './personal-update.component';
import { PersonalDeleteDialogComponent } from './personal-delete-dialog.component';
import { personalRoute } from './personal.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(personalRoute)],
  declarations: [PersonalComponent, PersonalDetailComponent, PersonalUpdateComponent, PersonalDeleteDialogComponent],
  entryComponents: [PersonalDeleteDialogComponent],
})
export class LabprovidenciaPersonalModule {}
