import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFQLeche } from 'app/shared/model/fq-leche.model';
import { FQLecheService } from './fq-leche.service';

@Component({
  templateUrl: './fq-leche-delete-dialog.component.html',
})
export class FQLecheDeleteDialogComponent {
  fQLeche?: IFQLeche;

  constructor(protected fQLecheService: FQLecheService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fQLecheService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fQLecheListModification');
      this.activeModal.close();
    });
  }
}
