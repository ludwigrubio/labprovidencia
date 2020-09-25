import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPruebaMicro } from 'app/shared/model/prueba-micro.model';
import { PruebaMicroService } from './prueba-micro.service';

@Component({
  templateUrl: './prueba-micro-delete-dialog.component.html',
})
export class PruebaMicroDeleteDialogComponent {
  pruebaMicro?: IPruebaMicro;

  constructor(
    protected pruebaMicroService: PruebaMicroService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pruebaMicroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pruebaMicroListModification');
      this.activeModal.close();
    });
  }
}
