import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPruebaMicro, PruebaMicro } from 'app/shared/model/prueba-micro.model';
import { PruebaMicroService } from './prueba-micro.service';
import { IArea } from 'app/shared/model/area.model';
import { AreaService } from 'app/entities/area/area.service';
import { ICultivo } from 'app/shared/model/cultivo.model';
import { CultivoService } from 'app/entities/cultivo/cultivo.service';
import { ISuperficie } from 'app/shared/model/superficie.model';
import { SuperficieService } from 'app/entities/superficie/superficie.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/user-extra.service';
import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from 'app/entities/personal/personal.service';
import { IProceso } from 'app/shared/model/proceso.model';
import { ProcesoService } from 'app/entities/proceso/proceso.service';

import { AccountService } from 'app/core/auth/account.service';
import { debounceTime } from 'rxjs/operators';
import { distinctUntilChanged } from 'rxjs/operators';
import { switchMap } from 'rxjs/operators';
import { map } from 'rxjs/operators';

type SelectableEntity = IArea | ICultivo | ISuperficie | IProducto | IUserExtra | IPersonal | IProceso;

@Component({
  selector: 'jhi-prueba-micro-update',
  templateUrl: './prueba-micro-update.component.html',
})
export class PruebaMicroUpdateComponent implements OnInit {
  isSaving = false;
  areas: IArea[] = [];
  cultivos: ICultivo[] = [];
  procesos: IProceso[] = [];

  editForm = this.fb.group({
    id: [],
    tipodeMuestra: [null, [Validators.required]],
    lote: [null, [Validators.maxLength(45)]],
    inicio: [null, [Validators.required]],
    fin: [],
    resultado: [],
    unidades: [],
    observaciones: [null, [Validators.maxLength(100)]],
    area: [null, Validators.required],
    cultivo: [],
    superficie: [],
    producto: [],
    analista: [null, Validators.required],
    proveedor: [null, Validators.required],
    proceso: [null],
  });

  constructor(
    protected pruebaMicroService: PruebaMicroService,
    protected areaService: AreaService,
    protected cultivoService: CultivoService,
    protected superficieService: SuperficieService,
    protected productoService: ProductoService,
    protected userExtraService: UserExtraService,
    protected personalService: PersonalService,
    protected procesoService: ProcesoService,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pruebaMicro }) => {
      if (!pruebaMicro.id) {
        const today = moment().startOf('day');
        pruebaMicro.inicio = today;
        pruebaMicro.fin = today;
      }

      this.updateForm(pruebaMicro);

      this.procesoService.query().subscribe((res: HttpResponse<IProceso[]>) => (this.procesos = res.body || []));

      this.accountService.getAuthenticationState().subscribe(account => {
        if (account!['id']) {
          this.userExtraService
            .query({ 'id.equals': account!['id'] })
            .subscribe((res: HttpResponse<IUserExtra[]>) => this.editForm.patchValue({ analista: res.body![0] }));
        }
      });

      this.cultivoService.query().subscribe((res: HttpResponse<ICultivo[]>) => (this.cultivos = res.body || []));

      this.editForm.controls['tipodeMuestra'].valueChanges.subscribe(value => {
        if (value === '2') {
          this.editForm.controls['producto'].setValue([]);
        } else {
          this.editForm.controls['superficie'].setValue([]);
        }
      });
    });
  }

  updateForm(pruebaMicro: IPruebaMicro): void {
    this.editForm.patchValue({
      id: pruebaMicro.id,
      tipodeMuestra: pruebaMicro.tipodeMuestra,
      lote: pruebaMicro.lote,
      inicio: pruebaMicro.inicio ? pruebaMicro.inicio.format(DATE_TIME_FORMAT) : null,
      fin: pruebaMicro.fin ? pruebaMicro.fin.format(DATE_TIME_FORMAT) : null,
      resultado: pruebaMicro.resultado,
      unidades: pruebaMicro.unidades,
      observaciones: pruebaMicro.observaciones,
      area: pruebaMicro.area,
      cultivo: pruebaMicro.cultivo,
      superficie: pruebaMicro.superficie,
      producto: pruebaMicro.producto,
      analista: pruebaMicro.analista,
      proveedor: pruebaMicro.proveedor,
      proceso: pruebaMicro.proceso,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pruebaMicro = this.createFromForm();
    if (pruebaMicro.id !== undefined) {
      this.subscribeToSaveResponse(this.pruebaMicroService.update(pruebaMicro));
    } else {
      this.subscribeToSaveResponse(this.pruebaMicroService.create(pruebaMicro));
    }
  }

  private createFromForm(): IPruebaMicro {
    return {
      ...new PruebaMicro(),
      id: this.editForm.get(['id'])!.value,
      tipodeMuestra: this.editForm.get(['tipodeMuestra'])!.value,
      lote: this.editForm.get(['lote'])!.value,
      inicio: this.editForm.get(['inicio'])!.value ? moment(this.editForm.get(['inicio'])!.value, DATE_TIME_FORMAT) : undefined,
      fin: this.editForm.get(['fin'])!.value ? moment(this.editForm.get(['fin'])!.value, DATE_TIME_FORMAT) : undefined,
      resultado: this.editForm.get(['resultado'])!.value,
      unidades: this.editForm.get(['unidades'])!.value,
      observaciones: this.editForm.get(['observaciones'])!.value,
      area: this.editForm.get(['area'])!.value,
      cultivo: this.editForm.get(['cultivo'])!.value,
      superficie: this.editForm.get(['superficie'])!.value,
      producto: this.editForm.get(['producto'])!.value,
      analista: this.editForm.get(['analista'])!.value,
      proveedor: this.editForm.get(['proveedor'])!.value,
      proceso: this.editForm.get(['proceso'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPruebaMicro>>): void {
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

  formatterSuperficie = (x: { superficie: string }) => x.superficie;

  searchSuperficie = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.superficieService.query({ 'superficie.contains': term }))),
      map((res: HttpResponse<ISuperficie[]>) => res.body || [])
    );

  formatterProducto = (x: { producto: string }) => x.producto;

  searchProducto = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.productoService.query({ 'producto.contains': term }))),
      map((res: HttpResponse<IProducto[]>) => res.body || [])
    );
}
