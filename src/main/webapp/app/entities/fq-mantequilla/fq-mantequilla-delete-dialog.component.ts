import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFQMantequilla } from 'app/shared/model/fq-mantequilla.model';
import { FQMantequillaService } from './fq-mantequilla.service';

@Component({
  templateUrl: './fq-mantequilla-delete-dialog.component.html',
})
export class FQMantequillaDeleteDialogComponent {
  fQMantequilla?: IFQMantequilla;

  constructor(
    protected fQMantequillaService: FQMantequillaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fQMantequillaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fQMantequillaListModification');
      this.activeModal.close();
    });
  }
}
