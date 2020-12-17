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
import { IContenedor } from 'app/shared/model/contenedor.model';
import { ContenedorService } from 'app/entities/contenedor/contenedor.service';
import { IProceso } from 'app/shared/model/proceso.model';
import { ProcesoService } from 'app/entities/proceso/proceso.service';

import { AccountService } from 'app/core/auth/account.service';
import { debounceTime } from 'rxjs/operators';
import { distinctUntilChanged } from 'rxjs/operators';
import { switchMap } from 'rxjs/operators';
import { map } from 'rxjs/operators';

type SelectableEntity = IArea | IProducto | IUserExtra | IPersonal | IProceso;

@Component({
  selector: 'jhi-fq-queso-update',
  templateUrl: './fq-queso-update.component.html',
})
export class FQQuesoUpdateComponent implements OnInit {
  isSaving = false;
  procesos: IProceso[] = [];

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
    dummy1: [],
    dummy2: [],
    dummy3: [],
    dummy4: [],
    dummy5: [],
    observaciones: [null, [Validators.maxLength(100)]],
    area: [null, Validators.required],
    producto: [null, Validators.required],
    analista: [null, Validators.required],
    proveedor: [null, Validators.required],
    contenedor: [null],
    proceso: [null],
  });

  constructor(
    protected fQQuesoService: FQQuesoService,
    protected areaService: AreaService,
    protected productoService: ProductoService,
    protected userExtraService: UserExtraService,
    protected personalService: PersonalService,
    protected contenedorService: ContenedorService,
    protected procesoService: ProcesoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fQQueso }) => {
      if (!fQQueso.id) {
        const today = moment();
        fQQueso.fecha = today;
        fQQueso.caducidad = today;
        fQQueso.lote = today.year().toString() + today.dayOfYear().toString();
      }

      this.updateForm(fQQueso);

      this.procesoService.query().subscribe((res: HttpResponse<IProceso[]>) => (this.procesos = res.body || []));

      this.accountService.getAuthenticationState().subscribe(account => {
        if (account!['id']) {
          this.userExtraService
            .query({ 'id.equals': account!['id'] })
            .subscribe((res: HttpResponse<IUserExtra[]>) => this.editForm.patchValue({ analista: res.body![0] }));
        }
      });
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
      dummy1: fQQueso.dummy1,
      dummy2: fQQueso.dummy2,
      dummy3: fQQueso.dummy3,
      dummy4: fQQueso.dummy4,
      dummy5: fQQueso.dummy5,
      observaciones: fQQueso.observaciones,
      area: fQQueso.area,
      producto: fQQueso.producto,
      analista: fQQueso.analista,
      proveedor: fQQueso.proveedor,
      contenedor: fQQueso.contenedor,
      proceso: fQQueso.proceso,
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
      dummy1: this.editForm.get(['dummy1'])!.value,
      dummy2: this.editForm.get(['dummy2'])!.value,
      dummy3: this.editForm.get(['dummy3'])!.value,
      dummy4: this.editForm.get(['dummy4'])!.value,
      dummy5: this.editForm.get(['dummy5'])!.value,
      observaciones: this.editForm.get(['observaciones'])!.value,
      area: this.editForm.get(['area'])!.value,
      producto: this.editForm.get(['producto'])!.value,
      analista: this.editForm.get(['analista'])!.value,
      proveedor: this.editForm.get(['proveedor'])!.value,
      contenedor: this.editForm.get(['contenedor'])!.value,
      proceso: this.editForm.get(['proceso'])!.value,
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

  formatterArea = (x: { area: string }) => x.area;

  searchArea = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.areaService.query({ 'area.contains': term }))),
      map((res: HttpResponse<IArea[]>) => res.body || [])
    );

  formatterProducto = (x: { producto: string }) => x.producto;

  searchProducto = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.productoService.query({ 'producto.contains': term }))),
      map((res: HttpResponse<IProducto[]>) => res.body || [])
    );

  formatterAnalista = (x: { nombreCompleto: string }) => x.nombreCompleto;

  searchAnalista = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.userExtraService.query({ 'nombreCompleto.contains': term }))),
      map((res: HttpResponse<IUserExtra[]>) => res.body || [])
    );

  formatterProveedor = (x: { nombre: string }) => x.nombre;

  searchProveedor = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.personalService.query({ 'nombre.contains': term, 'relacionId.equals': '2' }))),
      map((res: HttpResponse<IPersonal[]>) => res.body || [])
    );

  formatterContenedor = (x: { contenedor: string }) => x.contenedor;

  searchContenedor = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.contenedorService.query({ 'contenedor.contains': term }))),
      map((res: HttpResponse<IContenedor[]>) => res.body || [])
    );
}
