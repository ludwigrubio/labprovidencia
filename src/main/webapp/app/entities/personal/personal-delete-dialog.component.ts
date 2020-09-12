import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from './personal.service';

@Component({
  templateUrl: './personal-delete-dialog.component.html',
})
export class PersonalDeleteDialogComponent {
  personal?: IPersonal;

  constructor(protected personalService: PersonalService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.personalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('personalListModification');
      this.activeModal.close();
    });
  }
}
