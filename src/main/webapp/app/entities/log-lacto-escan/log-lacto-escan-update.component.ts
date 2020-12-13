import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILogLactoEscan, LogLactoEscan } from 'app/shared/model/log-lacto-escan.model';
import { LogLactoEscanService } from './log-lacto-escan.service';

@Component({
  selector: 'jhi-log-lacto-escan-update',
  templateUrl: './log-lacto-escan-update.component.html',
})
export class LogLactoEscanUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.required]],
    fecha: [null, [Validators.required]],
    nombreArchivo: [null, [Validators.required, Validators.maxLength(300)]],
    numeroFila: [],
    mensajeError: [null, [Validators.required, Validators.maxLength(300)]],
  });

  constructor(protected logLactoEscanService: LogLactoEscanService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logLactoEscan }) => {
      if (!logLactoEscan.id) {
        const today = moment().startOf('day');
        logLactoEscan.fecha = today;
      }

      this.updateForm(logLactoEscan);
    });
  }

  updateForm(logLactoEscan: ILogLactoEscan): void {
    this.editForm.patchValue({
      id: logLactoEscan.id,
      tipo: logLactoEscan.tipo,
      fecha: logLactoEscan.fecha ? logLactoEscan.fecha.format(DATE_TIME_FORMAT) : null,
      nombreArchivo: logLactoEscan.nombreArchivo,
      numeroFila: logLactoEscan.numeroFila,
      mensajeError: logLactoEscan.mensajeError,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const logLactoEscan = this.createFromForm();
    if (logLactoEscan.id !== undefined) {
      this.subscribeToSaveResponse(this.logLactoEscanService.update(logLactoEscan));
    } else {
      this.subscribeToSaveResponse(this.logLactoEscanService.create(logLactoEscan));
    }
  }

  private createFromForm(): ILogLactoEscan {
    return {
      ...new LogLactoEscan(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      nombreArchivo: this.editForm.get(['nombreArchivo'])!.value,
      numeroFila: this.editForm.get(['numeroFila'])!.value,
      mensajeError: this.editForm.get(['mensajeError'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILogLactoEscan>>): void {
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
