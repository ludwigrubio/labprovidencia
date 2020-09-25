import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecepcion } from 'app/shared/model/recepcion.model';
import { RecepcionService } from './recepcion.service';

@Component({
  templateUrl: './recepcion-delete-dialog.component.html',
})
export class RecepcionDeleteDialogComponent {
  recepcion?: IRecepcion;

  constructor(protected recepcionService: RecepcionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.recepcionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('recepcionListModification');
      this.activeModal.close();
    });
  }
}
