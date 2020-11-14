import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFQLeche, FQLeche } from 'app/shared/model/fq-leche.model';
import { FQLecheService } from './fq-leche.service';
import { IArea } from 'app/shared/model/area.model';
import { AreaService } from 'app/entities/area/area.service';
import { IRecepcion } from 'app/shared/model/recepcion.model';
import { RecepcionService } from 'app/entities/recepcion/recepcion.service';
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

type SelectableEntity = IArea | IRecepcion | IUserExtra | IPersonal;

@Component({
  selector: 'jhi-fq-leche-update',
  templateUrl: './fq-leche-update.component.html',
})
export class FQLecheUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fecha: [null, [Validators.required]],
    lote: [null, [Validators.maxLength(45)]],
    acidez: [],
    temperatura: [],
    agua: [],
    crioscopia: [],
    antibiotico: [],
    delvo: [],
    grasa: [],
    solidos: [null, [Validators.maxLength(45)]],
    densidad: [],
    lactosa: [],
    proteina: [],
    neutralizantes: [],
    adulterantes: [],
    reductasa: [],
    fosfatasa: [],
    ph: [],
    dummy1: [],
    dummy2: [],
    dummy3: [],
    dummy4: [],
    dummy5: [],
    observaciones: [null, [Validators.maxLength(100)]],
    area: [null, Validators.required],
    recepcion: [null, Validators.required],
    analista: [null, Validators.required],
    proveedor: [null, Validators.required],
    contenedor: [null],
  });

  constructor(
    protected fQLecheService: FQLecheService,
    protected areaService: AreaService,
    protected recepcionService: RecepcionService,
    protected userExtraService: UserExtraService,
    protected personalService: PersonalService,
    protected contenedorService: ContenedorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fQLeche }) => {
      if (!fQLeche.id) {
        const today = moment().startOf('day');
        fQLeche.fecha = today;
        fQLeche.lote = today.year().toString() + today.dayOfYear().toString();
      }

      this.updateForm(fQLeche);

      this.accountService.getAuthenticationState().subscribe(account => {
        if (account!['id']) {
          this.userExtraService
            .query({ 'id.equals': account!['id'] })
            .subscribe((res: HttpResponse<IUserExtra[]>) => this.editForm.patchValue({ analista: res.body![0] }));
        }
      });
    });
  }

  updateForm(fQLeche: IFQLeche): void {
    this.editForm.patchValue({
      id: fQLeche.id,
      fecha: fQLeche.fecha ? fQLeche.fecha.format(DATE_TIME_FORMAT) : null,
      lote: fQLeche.lote,
      acidez: fQLeche.acidez,
      temperatura: fQLeche.temperatura,
      agua: fQLeche.agua,
      crioscopia: fQLeche.crioscopia,
      antibiotico: fQLeche.antibiotico,
      delvo: fQLeche.delvo,
      grasa: fQLeche.grasa,
      solidos: fQLeche.solidos,
      densidad: fQLeche.densidad,
      lactosa: fQLeche.lactosa,
      proteina: fQLeche.proteina,
      neutralizantes: fQLeche.neutralizantes,
      adulterantes: fQLeche.adulterantes,
      reductasa: fQLeche.reductasa,
      fosfatasa: fQLeche.fosfatasa,
      ph: fQLeche.ph,
      dummy1: fQLeche.dummy1,
      dummy2: fQLeche.dummy2,
      dummy3: fQLeche.dummy3,
      dummy4: fQLeche.dummy4,
      dummy5: fQLeche.dummy5,
      observaciones: fQLeche.observaciones,
      area: fQLeche.area,
      recepcion: fQLeche.recepcion,
      analista: fQLeche.analista,
      proveedor: fQLeche.proveedor,
      contenedor: fQLeche.contenedor,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fQLeche = this.createFromForm();
    if (fQLeche.id !== undefined) {
      this.subscribeToSaveResponse(this.fQLecheService.update(fQLeche));
    } else {
      this.subscribeToSaveResponse(this.fQLecheService.create(fQLeche));
    }
  }

  private createFromForm(): IFQLeche {
    return {
      ...new FQLeche(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      lote: this.editForm.get(['lote'])!.value,
      acidez: this.editForm.get(['acidez'])!.value,
      temperatura: this.editForm.get(['temperatura'])!.value,
      agua: this.editForm.get(['agua'])!.value,
      crioscopia: this.editForm.get(['crioscopia'])!.value,
      antibiotico: this.editForm.get(['antibiotico'])!.value,
      delvo: this.editForm.get(['delvo'])!.value,
      grasa: this.editForm.get(['grasa'])!.value,
      solidos: this.editForm.get(['solidos'])!.value,
      densidad: this.editForm.get(['densidad'])!.value,
      lactosa: this.editForm.get(['lactosa'])!.value,
      proteina: this.editForm.get(['proteina'])!.value,
      neutralizantes: this.editForm.get(['neutralizantes'])!.value,
      adulterantes: this.editForm.get(['adulterantes'])!.value,
      reductasa: this.editForm.get(['reductasa'])!.value,
      fosfatasa: this.editForm.get(['fosfatasa'])!.value,
      ph: this.editForm.get(['ph'])!.value,
      dummy1: this.editForm.get(['dummy1'])!.value,
      dummy2: this.editForm.get(['dummy2'])!.value,
      dummy3: this.editForm.get(['dummy3'])!.value,
      dummy4: this.editForm.get(['dummy4'])!.value,
      dummy5: this.editForm.get(['dummy5'])!.value,
      observaciones: this.editForm.get(['observaciones'])!.value,
      area: this.editForm.get(['area'])!.value,
      recepcion: this.editForm.get(['recepcion'])!.value,
      analista: this.editForm.get(['analista'])!.value,
      proveedor: this.editForm.get(['proveedor'])!.value,
      contenedor: this.editForm.get(['contenedor'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFQLeche>>): void {
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

  formatterRecepcion = (x: { id: string }) => x.id;

  searchRecepcion = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => (term.length < 1 ? [] : this.recepcionService.query({ 'id.equals': term }))),
      map((res: HttpResponse<IRecepcion[]>) => res.body || [])
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
