import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICultivo } from 'app/shared/model/cultivo.model';
import { CultivoService } from './cultivo.service';

@Component({
  templateUrl: './cultivo-delete-dialog.component.html',
})
export class CultivoDeleteDialogComponent {
  cultivo?: ICultivo;

  constructor(protected cultivoService: CultivoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cultivoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cultivoListModification');
      this.activeModal.close();
    });
  }
}
