import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRelacion, Relacion } from 'app/shared/model/relacion.model';
import { RelacionService } from './relacion.service';

@Component({
  selector: 'jhi-relacion-update',
  templateUrl: './relacion-update.component.html',
})
export class RelacionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    relacion: [null, [Validators.required, Validators.maxLength(45)]],
  });

  constructor(protected relacionService: RelacionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ relacion }) => {
      this.updateForm(relacion);
    });
  }

  updateForm(relacion: IRelacion): void {
    this.editForm.patchValue({
      id: relacion.id,
      relacion: relacion.relacion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const relacion = this.createFromForm();
    if (relacion.id !== undefined) {
      this.subscribeToSaveResponse(this.relacionService.update(relacion));
    } else {
      this.subscribeToSaveResponse(this.relacionService.create(relacion));
    }
  }

  private createFromForm(): IRelacion {
    return {
      ...new Relacion(),
      id: this.editForm.get(['id'])!.value,
      relacion: this.editForm.get(['relacion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRelacion>>): void {
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
