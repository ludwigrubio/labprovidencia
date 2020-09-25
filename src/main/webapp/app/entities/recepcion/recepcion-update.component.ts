import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRecepcion, Recepcion } from 'app/shared/model/recepcion.model';
import { RecepcionService } from './recepcion.service';
import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from 'app/entities/personal/personal.service';

@Component({
  selector: 'jhi-recepcion-update',
  templateUrl: './recepcion-update.component.html',
})
export class RecepcionUpdateComponent implements OnInit {
  isSaving = false;
  personals: IPersonal[] = [];

  editForm = this.fb.group({
    id: [],
    idProveedor: [null, [Validators.required]],
    litros: [null, [Validators.required]],
    tiempo: [null, [Validators.required]],
    turno: [null, [Validators.required, Validators.maxLength(1)]],
    incentivoLT: [],
    incentivoT: [],
    proveedor: [null, Validators.required],
  });

  constructor(
    protected recepcionService: RecepcionService,
    protected personalService: PersonalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recepcion }) => {
      if (!recepcion.id) {
        const today = moment().startOf('day');
        recepcion.tiempo = today;
      }

      this.updateForm(recepcion);

      this.personalService.query().subscribe((res: HttpResponse<IPersonal[]>) => (this.personals = res.body || []));
    });
  }

  updateForm(recepcion: IRecepcion): void {
    this.editForm.patchValue({
      id: recepcion.id,
      idProveedor: recepcion.idProveedor,
      litros: recepcion.litros,
      tiempo: recepcion.tiempo ? recepcion.tiempo.format(DATE_TIME_FORMAT) : null,
      turno: recepcion.turno,
      incentivoLT: recepcion.incentivoLT,
      incentivoT: recepcion.incentivoT,
      proveedor: recepcion.proveedor,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recepcion = this.createFromForm();
    if (recepcion.id !== undefined) {
      this.subscribeToSaveResponse(this.recepcionService.update(recepcion));
    } else {
      this.subscribeToSaveResponse(this.recepcionService.create(recepcion));
    }
  }

  private createFromForm(): IRecepcion {
    return {
      ...new Recepcion(),
      id: this.editForm.get(['id'])!.value,
      idProveedor: this.editForm.get(['idProveedor'])!.value,
      litros: this.editForm.get(['litros'])!.value,
      tiempo: this.editForm.get(['tiempo'])!.value ? moment(this.editForm.get(['tiempo'])!.value, DATE_TIME_FORMAT) : undefined,
      turno: this.editForm.get(['turno'])!.value,
      incentivoLT: this.editForm.get(['incentivoLT'])!.value,
      incentivoT: this.editForm.get(['incentivoT'])!.value,
      proveedor: this.editForm.get(['proveedor'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecepcion>>): void {
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

  trackById(index: number, item: IPersonal): any {
    return item.id;
  }
}
