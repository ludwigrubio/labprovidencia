import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFQQueso, FQQueso } from 'app/shared/model/fq-queso.model';
import { FQQuesoService } from './fq-queso.service';
import { IArea } from 'app/shared/model/area.model';
import { AreaService } from 'app/entities/area/area.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/user-extra.service';
import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from 'app/entities/personal/personal.service';

type SelectableEntity = IArea | IProducto | IUserExtra | IPersonal;

@Component({
  selector: 'jhi-fq-queso-update',
  templateUrl: './fq-queso-update.component.html',
})
export class FQQuesoUpdateComponent implements OnInit {
  isSaving = false;
  areas: IArea[] = [];
  productos: IProducto[] = [];
  userextras: IUserExtra[] = [];
  personals: IPersonal[] = [];

  editForm = this.fb.group({
    id: [],
    fecha: [null, [Validators.required]],
    lote: [null, [Validators.required, Validators.maxLength(45)]],
    humedad: [],
    ph: [],
    fundicion: [],
    presentacion: [],
    caducidad: [],
    apariencia: [],
    sabor: [],
    color: [],
    olor: [],
    textura: [],
    hilado: [],
    observaciones: [null, [Validators.maxLength(100)]],
    area: [null, Validators.required],
    producto: [null, Validators.required],
    analista: [null, Validators.required],
    proveedor: [null, Validators.required],
  });

  constructor(
    protected fQQuesoService: FQQuesoService,
    protected areaService: AreaService,
    protected productoService: ProductoService,
    protected userExtraService: UserExtraService,
    protected personalService: PersonalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fQQueso }) => {
      if (!fQQueso.id) {
        const today = moment().startOf('day');
        fQQueso.fecha = today;
        fQQueso.caducidad = today;
      }

      this.updateForm(fQQueso);

      this.areaService.query().subscribe((res: HttpResponse<IArea[]>) => (this.areas = res.body || []));

      this.productoService.query().subscribe((res: HttpResponse<IProducto[]>) => (this.productos = res.body || []));

      this.userExtraService.query().subscribe((res: HttpResponse<IUserExtra[]>) => (this.userextras = res.body || []));

      this.personalService.query().subscribe((res: HttpResponse<IPersonal[]>) => (this.personals = res.body || []));
    });
  }

  updateForm(fQQueso: IFQQueso): void {
    this.editForm.patchValue({
      id: fQQueso.id,
      fecha: fQQueso.fecha ? fQQueso.fecha.format(DATE_TIME_FORMAT) : null,
      lote: fQQueso.lote,
      humedad: fQQueso.humedad,
      ph: fQQueso.ph,
      fundicion: fQQueso.fundicion,
      presentacion: fQQueso.presentacion,
      caducidad: fQQueso.caducidad ? fQQueso.caducidad.format(DATE_TIME_FORMAT) : null,
      apariencia: fQQueso.apariencia,
      sabor: fQQueso.sabor,
      color: fQQueso.color,
      olor: fQQueso.olor,
      textura: fQQueso.textura,
      hilado: fQQueso.hilado,
      observaciones: fQQueso.observaciones,
      area: fQQueso.area,
      producto: fQQueso.producto,
      analista: fQQueso.analista,
      proveedor: fQQueso.proveedor,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fQQueso = this.createFromForm();
    if (fQQueso.id !== undefined) {
      this.subscribeToSaveResponse(this.fQQuesoService.update(fQQueso));
    } else {
      this.subscribeToSaveResponse(this.fQQuesoService.create(fQQueso));
    }
  }

  private createFromForm(): IFQQueso {
    return {
      ...new FQQueso(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      lote: this.editForm.get(['lote'])!.value,
      humedad: this.editForm.get(['humedad'])!.value,
      ph: this.editForm.get(['ph'])!.value,
      fundicion: this.editForm.get(['fundicion'])!.value,
      presentacion: this.editForm.get(['presentacion'])!.value,
      caducidad: this.editForm.get(['caducidad'])!.value ? moment(this.editForm.get(['caducidad'])!.value, DATE_TIME_FORMAT) : undefined,
      apariencia: this.editForm.get(['apariencia'])!.value,
      sabor: this.editForm.get(['sabor'])!.value,
      color: this.editForm.get(['color'])!.value,
      olor: this.editForm.get(['olor'])!.value,
      textura: this.editForm.get(['textura'])!.value,
      hilado: this.editForm.get(['hilado'])!.value,
      observaciones: this.editForm.get(['observaciones'])!.value,
      area: this.editForm.get(['area'])!.value,
      producto: this.editForm.get(['producto'])!.value,
      analista: this.editForm.get(['analista'])!.value,
      proveedor: this.editForm.get(['proveedor'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFQQueso>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}