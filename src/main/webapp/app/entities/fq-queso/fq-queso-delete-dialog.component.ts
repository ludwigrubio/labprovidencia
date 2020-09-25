import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFQQueso } from 'app/shared/model/fq-queso.model';
import { FQQuesoService } from './fq-queso.service';

@Component({
  templateUrl: './fq-queso-delete-dialog.component.html',
})
export class FQQuesoDeleteDialogComponent {
  fQQueso?: IFQQueso;

  constructor(protected fQQuesoService: FQQuesoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fQQuesoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fQQuesoListModification');
      this.activeModal.close();
    });
  }
}
