import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFQCrema } from 'app/shared/model/fq-crema.model';
import { FQCremaService } from './fq-crema.service';

@Component({
  templateUrl: './fq-crema-delete-dialog.component.html',
})
export class FQCremaDeleteDialogComponent {
  fQCrema?: IFQCrema;

  constructor(protected fQCremaService: FQCremaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fQCremaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fQCremaListModification');
      this.activeModal.close();
    });
  }
}
