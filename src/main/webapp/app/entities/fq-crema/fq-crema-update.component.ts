import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFQCrema, FQCrema } from 'app/shared/model/fq-crema.model';
import { FQCremaService } from './fq-crema.service';
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
  selector: 'jhi-fq-crema-update',
  templateUrl: './fq-crema-update.component.html',
})
export class FQCremaUpdateComponent implements OnInit {
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
    grasa: [],
    observaciones: [null, [Validators.maxLength(100)]],
    area: [null, Validators.required],
    producto: [null, Validators.required],
    analista: [null, Validators.required],
    proveedor: [null, Validators.required],
  });

  constructor(
    protected fQCremaService: FQCremaService,
    protected areaService: AreaService,
    protected productoService: ProductoService,
    protected userExtraService: UserExtraService,
    protected personalService: PersonalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fQCrema }) => {
      if (!fQCrema.id) {
        const today = moment().startOf('day');
        fQCrema.fecha = today;
      }

      this.updateForm(fQCrema);

      this.areaService.query().subscribe((res: HttpResponse<IArea[]>) => (this.areas = res.body || []));

      this.productoService.query().subscribe((res: HttpResponse<IProducto[]>) => (this.productos = res.body || []));

      this.userExtraService.query().subscribe((res: HttpResponse<IUserExtra[]>) => (this.userextras = res.body || []));

      this.personalService.query().subscribe((res: HttpResponse<IPersonal[]>) => (this.personals = res.body || []));
    });
  }

  updateForm(fQCrema: IFQCrema): void {
    this.editForm.patchValue({
      id: fQCrema.id,
      fecha: fQCrema.fecha ? fQCrema.fecha.format(DATE_TIME_FORMAT) : null,
      lote: fQCrema.lote,
      acidez: fQCrema.acidez,
      grasa: fQCrema.grasa,
      observaciones: fQCrema.observaciones,
      area: fQCrema.area,
      producto: fQCrema.producto,
      analista: fQCrema.analista,
      proveedor: fQCrema.proveedor,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fQCrema = this.createFromForm();
    if (fQCrema.id !== undefined) {
      this.subscribeToSaveResponse(this.fQCremaService.update(fQCrema));
    } else {
      this.subscribeToSaveResponse(this.fQCremaService.create(fQCrema));
    }
  }

  private createFromForm(): IFQCrema {
    return {
      ...new FQCrema(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      lote: this.editForm.get(['lote'])!.value,
      acidez: this.editForm.get(['acidez'])!.value,
      grasa: this.editForm.get(['grasa'])!.value,
      observaciones: this.editForm.get(['observaciones'])!.value,
      area: this.editForm.get(['area'])!.value,
      producto: this.editForm.get(['producto'])!.value,
      analista: this.editForm.get(['analista'])!.value,
      proveedor: this.editForm.get(['proveedor'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFQCrema>>): void {
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