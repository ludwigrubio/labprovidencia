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

type SelectableEntity = IArea | IRecepcion | IUserExtra | IPersonal;

@Component({
  selector: 'jhi-fq-leche-update',
  templateUrl: './fq-leche-update.component.html',
})
export class FQLecheUpdateComponent implements OnInit {
  isSaving = false;
  areas: IArea[] = [];
  recepcions: IRecepcion[] = [];
  userextras: IUserExtra[] = [];
  personals: IPersonal[] = [];

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
    observaciones: [null, [Validators.maxLength(100)]],
    area: [null, Validators.required],
    recepcion: [null, Validators.required],
    analista: [null, Validators.required],
    proveedor: [null, Validators.required],
  });

  constructor(
    protected fQLecheService: FQLecheService,
    protected areaService: AreaService,
    protected recepcionService: RecepcionService,
    protected userExtraService: UserExtraService,
    protected personalService: PersonalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fQLeche }) => {
      if (!fQLeche.id) {
        const today = moment().startOf('day');
        fQLeche.fecha = today;
      }

      this.updateForm(fQLeche);

      this.areaService.query().subscribe((res: HttpResponse<IArea[]>) => (this.areas = res.body || []));

      this.recepcionService.query().subscribe((res: HttpResponse<IRecepcion[]>) => (this.recepcions = res.body || []));

      this.userExtraService.query().subscribe((res: HttpResponse<IUserExtra[]>) => (this.userextras = res.body || []));

      this.personalService.query().subscribe((res: HttpResponse<IPersonal[]>) => (this.personals = res.body || []));
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
      observaciones: fQLeche.observaciones,
      area: fQLeche.area,
      recepcion: fQLeche.recepcion,
      analista: fQLeche.analista,
      proveedor: fQLeche.proveedor,
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
      observaciones: this.editForm.get(['observaciones'])!.value,
      area: this.editForm.get(['area'])!.value,
      recepcion: this.editForm.get(['recepcion'])!.value,
      analista: this.editForm.get(['analista'])!.value,
      proveedor: this.editForm.get(['proveedor'])!.value,
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
}