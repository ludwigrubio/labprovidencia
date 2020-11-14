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
import { IContenedor } from 'app/shared/model/contenedor.model';
import { ContenedorService } from 'app/entities/contenedor/contenedor.service';

import { AccountService } from 'app/core/auth/account.service';
import { debounceTime } from 'rxjs/operators';
import { distinctUntilChanged } from 'rxjs/operators';
import { switchMap } from 'rxjs/operators';
import { map } from 'rxjs/operators';

type SelectableEntity = IArea | IProducto | IUserExtra | IPersonal;

@Component({
  selector: 'jhi-fq-suero-update',
  templateUrl: './fq-suero-update.component.html',
})
export class FQSueroUpdateComponent implements OnInit {
  isSaving = false;

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
  });

  constructor(
    protected fQSueroService: FQSueroService,
    protected areaService: AreaService,
    protected productoService: ProductoService,
    protected userExtraService: UserExtraService,
    protected personalService: PersonalService,
    protected contenedorService: ContenedorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fQSuero }) => {
      if (!fQSuero.id) {
        const today = moment();
        fQSuero.fecha = today;
        fQSuero.lote = today.year().toString() + today.dayOfYear().toString();
      }

      this.updateForm(fQSuero);

      this.accountService.getAuthenticationState().subscribe(account => {
        if (account!['id']) {
          this.userExtraService
            .query({ 'id.equals': account!['id'] })
            .subscribe((res: HttpResponse<IUserExtra[]>) => this.editForm.patchValue({ analista: res.body![0] }));
        }
      });
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
      dummy1: fQSuero.dummy1,
      dummy2: fQSuero.dummy2,
      dummy3: fQSuero.dummy3,
      dummy4: fQSuero.dummy4,
      dummy5: fQSuero.dummy5,
      observaciones: fQSuero.observaciones,
      area: fQSuero.area,
      producto: fQSuero.producto,
      analista: fQSuero.analista,
      proveedor: fQSuero.proveedor,
      contenedor: fQSuero.contenedor,
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
