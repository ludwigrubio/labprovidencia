import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContenedor } from 'app/shared/model/contenedor.model';
import { ContenedorService } from './contenedor.service';

@Component({
  templateUrl: './contenedor-delete-dialog.component.html',
})
export class ContenedorDeleteDialogComponent {
  contenedor?: IContenedor;

  constructor(
    protected contenedorService: ContenedorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contenedorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contenedorListModification');
      this.activeModal.close();
    });
  }
}
