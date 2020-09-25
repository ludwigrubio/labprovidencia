import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFQSuero, FQSuero } from 'app/shared/model/fq-suero.model';
import { FQSueroService } from './fq-suero.service';
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
  selector: 'jhi-fq-suero-update',
  templateUrl: './fq-suero-update.component.html',
})
export class FQSueroUpdateComponent implements OnInit {
  isSaving = false;
  areas: IArea[] = [];
  productos: IProducto[] = [];
  userextras: IUserExtra[] = [];
  personals: IPersonal[] = [];

  editForm = this.fb.group({
    id: [],
    fecha: [null, [Validators.required]],
    lote: [null, [Validators.required, Validators.maxLength(45)]],
    acidez: [],
    temperatura: [],
    delvo: [],
    solidos: [],
    neutralizantes: [],
    ph: [],
    cloro: [],
    almidon: [],
    observaciones: [null, [Validators.maxLength(100)]],
    area: [null, Validators.required],
    producto: [null, Validators.required],
    analista: [null, Validators.required],
    proveedor: [null, Validators.required],
  });

  constructor(
    protected fQSueroService: FQSueroService,
    protected areaService: AreaService,
    protected productoService: ProductoService,
    protected userExtraService: UserExtraService,
    protected personalService: PersonalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fQSuero }) => {
      if (!fQSuero.id) {
        const today = moment().startOf('day');
        fQSuero.fecha = today;
      }

      this.updateForm(fQSuero);

      this.areaService.query().subscribe((res: HttpResponse<IArea[]>) => (this.areas = res.body || []));

      this.productoService.query().subscribe((res: HttpResponse<IProducto[]>) => (this.productos = res.body || []));

      this.userExtraService.query().subscribe((res: HttpResponse<IUserExtra[]>) => (this.userextras = res.body || []));

      this.personalService.query().subscribe((res: HttpResponse<IPersonal[]>) => (this.personals = res.body || []));
    });
  }

  updateForm(fQSuero: IFQSuero): void {
    this.editForm.patchValue({
      id: fQSuero.id,
      fecha: fQSuero.fecha ? fQSuero.fecha.format(DATE_TIME_FORMAT) : null,
      lote: fQSuero.lote,
      acidez: fQSuero.acidez,
      temperatura: fQSuero.temperatura,
      delvo: fQSuero.delvo,
      solidos: fQSuero.solidos,
      neutralizantes: fQSuero.neutralizantes,
      ph: fQSuero.ph,
      cloro: fQSuero.cloro,
      almidon: fQSuero.almidon,
      observaciones: fQSuero.observaciones,
      area: fQSuero.area,
      producto: fQSuero.producto,
      analista: fQSuero.analista,
      proveedor: fQSuero.proveedor,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fQSuero = this.createFromForm();
    if (fQSuero.id !== undefined) {
      this.subscribeToSaveResponse(this.fQSueroService.update(fQSuero));
    } else {
      this.subscribeToSaveResponse(this.fQSueroService.create(fQSuero));
    }
  }

  private createFromForm(): IFQSuero {
    return {
      ...new FQSuero(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      lote: this.editForm.get(['lote'])!.value,
      acidez: this.editForm.get(['acidez'])!.value,
      temperatura: this.editForm.get(['temperatura'])!.value,
      delvo: this.editForm.get(['delvo'])!.value,
      solidos: this.editForm.get(['solidos'])!.value,
      neutralizantes: this.editForm.get(['neutralizantes'])!.value,
      ph: this.editForm.get(['ph'])!.value,
      cloro: this.editForm.get(['cloro'])!.value,
      almidon: this.editForm.get(['almidon'])!.value,
      observaciones: this.editForm.get(['observaciones'])!.value,
      area: this.editForm.get(['area'])!.value,
      producto: this.editForm.get(['producto'])!.value,
      analista: this.editForm.get(['analista'])!.value,
      proveedor: this.editForm.get(['proveedor'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFQSuero>>): void {
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
