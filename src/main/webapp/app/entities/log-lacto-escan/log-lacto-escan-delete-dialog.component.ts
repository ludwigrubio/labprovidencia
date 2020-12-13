import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILogLactoEscan } from 'app/shared/model/log-lacto-escan.model';
import { LogLactoEscanService } from './log-lacto-escan.service';

@Component({
  templateUrl: './log-lacto-escan-delete-dialog.component.html',
})
export class LogLactoEscanDeleteDialogComponent {
  logLactoEscan?: ILogLactoEscan;

  constructor(
    protected logLactoEscanService: LogLactoEscanService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.logLactoEscanService.delete(id).subscribe(() => {
      this.eventManager.broadcast('logLactoEscanListModification');
      this.activeModal.close();
    });
  }
}
