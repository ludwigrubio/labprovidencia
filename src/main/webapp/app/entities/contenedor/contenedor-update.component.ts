import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContenedor, Contenedor } from 'app/shared/model/contenedor.model';
import { ContenedorService } from './contenedor.service';

@Component({
  selector: 'jhi-contenedor-update',
  templateUrl: './contenedor-update.component.html',
})
export class ContenedorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    contenedor: [null, [Validators.required, Validators.maxLength(45)]],
  });

  constructor(protected contenedorService: ContenedorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contenedor }) => {
      this.updateForm(contenedor);
    });
  }

  updateForm(contenedor: IContenedor): void {
    this.editForm.patchValue({
      id: contenedor.id,
      contenedor: contenedor.contenedor,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contenedor = this.createFromForm();
    if (contenedor.id !== undefined) {
      this.subscribeToSaveResponse(this.contenedorService.update(contenedor));
    } else {
      this.subscribeToSaveResponse(this.contenedorService.create(contenedor));
    }
  }

  private createFromForm(): IContenedor {
    return {
      ...new Contenedor(),
      id: this.editForm.get(['id'])!.value,
      contenedor: this.editForm.get(['contenedor'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContenedor>>): void {
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
