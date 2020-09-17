import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDummy } from 'app/shared/model/dummy.model';
import { DummyService } from './dummy.service';

@Component({
  templateUrl: './dummy-delete-dialog.component.html',
})
export class DummyDeleteDialogComponent {
  dummy?: IDummy;

  constructor(protected dummyService: DummyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dummyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dummyListModification');
      this.activeModal.close();
    });
  }
}
