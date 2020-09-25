import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISuperficie, Superficie } from 'app/shared/model/superficie.model';
import { SuperficieService } from './superficie.service';

@Component({
  selector: 'jhi-superficie-update',
  templateUrl: './superficie-update.component.html',
})
export class SuperficieUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    superficie: [null, [Validators.required, Validators.maxLength(45)]],
    descripcion: [null, [Validators.maxLength(200)]],
  });

  constructor(protected superficieService: SuperficieService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ superficie }) => {
      this.updateForm(superficie);
    });
  }

  updateForm(superficie: ISuperficie): void {
    this.editForm.patchValue({
      id: superficie.id,
      superficie: superficie.superficie,
      descripcion: superficie.descripcion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const superficie = this.createFromForm();
    if (superficie.id !== undefined) {
      this.subscribeToSaveResponse(this.superficieService.update(superficie));
    } else {
      this.subscribeToSaveResponse(this.superficieService.create(superficie));
    }
  }

  private createFromForm(): ISuperficie {
    return {
      ...new Superficie(),
      id: this.editForm.get(['id'])!.value,
      superficie: this.editForm.get(['superficie'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISuperficie>>): void {
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
