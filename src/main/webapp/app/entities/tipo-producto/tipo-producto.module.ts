import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabprovidenciaSharedModule } from 'app/shared/shared.module';
import { TipoProductoComponent } from './tipo-producto.component';
import { TipoProductoDetailComponent } from './tipo-producto-detail.component';
import { TipoProductoUpdateComponent } from './tipo-producto-update.component';
import { TipoProductoDeleteDialogComponent } from './tipo-producto-delete-dialog.component';
import { tipoProductoRoute } from './tipo-producto.route';

@NgModule({
  imports: [LabprovidenciaSharedModule, RouterModule.forChild(tipoProductoRoute)],
  declarations: [TipoProductoComponent, TipoProductoDetailComponent, TipoProductoUpdateComponent, TipoProductoDeleteDialogComponent],
  entryComponents: [TipoProductoDeleteDialogComponent],
})
export class LabprovidenciaTipoProductoModule {}
