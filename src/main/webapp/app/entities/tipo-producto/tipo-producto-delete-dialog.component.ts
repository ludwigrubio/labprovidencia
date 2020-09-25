import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoProducto } from 'app/shared/model/tipo-producto.model';
import { TipoProductoService } from './tipo-producto.service';

@Component({
  templateUrl: './tipo-producto-delete-dialog.component.html',
})
export class TipoProductoDeleteDialogComponent {
  tipoProducto?: ITipoProducto;

  constructor(
    protected tipoProductoService: TipoProductoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoProductoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoProductoListModification');
      this.activeModal.close();
    });
  }
}
