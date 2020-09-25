import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoProducto, TipoProducto } from 'app/shared/model/tipo-producto.model';
import { TipoProductoService } from './tipo-producto.service';

@Component({
  selector: 'jhi-tipo-producto-update',
  templateUrl: './tipo-producto-update.component.html',
})
export class TipoProductoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.required, Validators.maxLength(45)]],
  });

  constructor(protected tipoProductoService: TipoProductoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoProducto }) => {
      this.updateForm(tipoProducto);
    });
  }

  updateForm(tipoProducto: ITipoProducto): void {
    this.editForm.patchValue({
      id: tipoProducto.id,
      tipo: tipoProducto.tipo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoProducto = this.createFromForm();
    if (tipoProducto.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoProductoService.update(tipoProducto));
    } else {
      this.subscribeToSaveResponse(this.tipoProductoService.create(tipoProducto));
    }
  }

  private createFromForm(): ITipoProducto {
    return {
      ...new TipoProducto(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoProducto>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
