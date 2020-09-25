import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISuperficie } from 'app/shared/model/superficie.model';
import { SuperficieService } from './superficie.service';

@Component({
  templateUrl: './superficie-delete-dialog.component.html',
})
export class SuperficieDeleteDialogComponent {
  superficie?: ISuperficie;

  constructor(
    protected superficieService: SuperficieService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.superficieService.delete(id).subscribe(() => {
      this.eventManager.broadcast('superficieListModification');
      this.activeModal.close();
    });
  }
}
