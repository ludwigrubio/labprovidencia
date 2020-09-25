import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICultivo, Cultivo } from 'app/shared/model/cultivo.model';
import { CultivoService } from './cultivo.service';

@Component({
  selector: 'jhi-cultivo-update',
  templateUrl: './cultivo-update.component.html',
})
export class CultivoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    cultivo: [null, [Validators.required, Validators.maxLength(45)]],
  });

  constructor(protected cultivoService: CultivoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cultivo }) => {
      this.updateForm(cultivo);
    });
  }

  updateForm(cultivo: ICultivo): void {
    this.editForm.patchValue({
      id: cultivo.id,
      cultivo: cultivo.cultivo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cultivo = this.createFromForm();
    if (cultivo.id !== undefined) {
      this.subscribeToSaveResponse(this.cultivoService.update(cultivo));
    } else {
      this.subscribeToSaveResponse(this.cultivoService.create(cultivo));
    }
  }

  private createFromForm(): ICultivo {
    return {
      ...new Cultivo(),
      id: this.editForm.get(['id'])!.value,
      cultivo: this.editForm.get(['cultivo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICultivo>>): void {
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
