import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRelacion } from 'app/shared/model/relacion.model';
import { RelacionService } from './relacion.service';

@Component({
  templateUrl: './relacion-delete-dialog.component.html',
})
export class RelacionDeleteDialogComponent {
  relacion?: IRelacion;

  constructor(protected relacionService: RelacionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.relacionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('relacionListModification');
      this.activeModal.close();
    });
  }
}
