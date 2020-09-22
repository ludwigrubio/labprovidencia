import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPersonal, Personal } from 'app/shared/model/personal.model';
import { PersonalService } from './personal.service';
import { IRelacion } from 'app/shared/model/relacion.model';
import { RelacionService } from 'app/entities/relacion/relacion.service';

@Component({
  selector: 'jhi-personal-update',
  templateUrl: './personal-update.component.html',
})
export class PersonalUpdateComponent implements OnInit {
  isSaving = false;
  relacions: IRelacion[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(100)]],
    apellido1: [null, [Validators.maxLength(50)]],
    apellido2: [null, [Validators.maxLength(45)]],
    alias: [null, [Validators.maxLength(45)]],
    domicilio: [null, [Validators.required, Validators.maxLength(200)]],
    colonia: [null, [Validators.required, Validators.maxLength(45)]],
    localidad: [null, [Validators.maxLength(45)]],
    estado: [null, [Validators.required, Validators.maxLength(45)]],
    pais: [null, [Validators.required, Validators.maxLength(45)]],
    latitud: [null, [Validators.maxLength(20)]],
    longitud: [null, [Validators.maxLength(20)]],
    cp: [null, [Validators.required]],
    telefono: [null, [Validators.required, Validators.maxLength(45)]],
    email: [null, [Validators.maxLength(45)]],
    rfc: [null, [Validators.required, Validators.maxLength(45)]],
    inicio: [null, [Validators.required]],
    fin: [],
    cargo: [null, [Validators.required, Validators.maxLength(45)]],
    comentario: [null, [Validators.maxLength(300)]],
    relacion: [null, Validators.required],
  });

  constructor(
    protected personalService: PersonalService,
    protected relacionService: RelacionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personal }) => {
      if (!personal.id) {
        const today = moment().startOf('day');
        personal.inicio = today;
        personal.fin = today;
      }

      this.updateForm(personal);

      this.relacionService.query().subscribe((res: HttpResponse<IRelacion[]>) => (this.relacions = res.body || []));
    });
  }

  updateForm(personal: IPersonal): void {
    this.editForm.patchValue({
      id: personal.id,
      nombre: personal.nombre,
      apellido1: personal.apellido1,
      apellido2: personal.apellido2,
      alias: personal.alias,
      domicilio: personal.domicilio,
      colonia: personal.colonia,
      localidad: personal.localidad,
      estado: personal.estado,
      pais: personal.pais,
      latitud: personal.latitud,
      longitud: personal.longitud,
      cp: personal.cp,
      telefono: personal.telefono,
      email: personal.email,
      rfc: personal.rfc,
      inicio: personal.inicio ? personal.inicio.format(DATE_TIME_FORMAT) : null,
      fin: personal.fin ? personal.fin.format(DATE_TIME_FORMAT) : null,
      cargo: personal.cargo,
      comentario: personal.comentario,
      relacion: personal.relacion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personal = this.createFromForm();
    if (personal.id !== undefined) {
      this.subscribeToSaveResponse(this.personalService.update(personal));
    } else {
      this.subscribeToSaveResponse(this.personalService.create(personal));
    }
  }

  private createFromForm(): IPersonal {
    return {
      ...new Personal(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      apellido1: this.editForm.get(['apellido1'])!.value,
      apellido2: this.editForm.get(['apellido2'])!.value,
      alias: this.editForm.get(['alias'])!.value,
      domicilio: this.editForm.get(['domicilio'])!.value,
      colonia: this.editForm.get(['colonia'])!.value,
      localidad: this.editForm.get(['localidad'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      pais: this.editForm.get(['pais'])!.value,
      latitud: this.editForm.get(['latitud'])!.value,
      longitud: this.editForm.get(['longitud'])!.value,
      cp: this.editForm.get(['cp'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      email: this.editForm.get(['email'])!.value,
      rfc: this.editForm.get(['rfc'])!.value,
      inicio: this.editForm.get(['inicio'])!.value ? moment(this.editForm.get(['inicio'])!.value, DATE_TIME_FORMAT) : undefined,
      fin: this.editForm.get(['fin'])!.value ? moment(this.editForm.get(['fin'])!.value, DATE_TIME_FORMAT) : undefined,
      cargo: this.editForm.get(['cargo'])!.value,
      comentario: this.editForm.get(['comentario'])!.value,
      relacion: this.editForm.get(['relacion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonal>>): void {
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

  trackById(index: number, item: IRelacion): any {
    return item.id;
  }
}
