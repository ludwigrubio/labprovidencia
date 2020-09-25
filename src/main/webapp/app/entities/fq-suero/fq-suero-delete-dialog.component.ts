import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFQSuero } from 'app/shared/model/fq-suero.model';
import { FQSueroService } from './fq-suero.service';

@Component({
  templateUrl: './fq-suero-delete-dialog.component.html',
})
export class FQSueroDeleteDialogComponent {
  fQSuero?: IFQSuero;

  constructor(protected fQSueroService: FQSueroService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fQSueroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fQSueroListModification');
      this.activeModal.close();
    });
  }
}
