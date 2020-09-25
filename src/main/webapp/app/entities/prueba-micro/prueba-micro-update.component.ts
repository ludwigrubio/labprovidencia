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
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/user-extra.service';
import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from 'app/entities/personal/personal.service';

type SelectableEntity = IArea | ICultivo | ISuperficie | IUserExtra | IPersonal;

@Component({
  selector: 'jhi-prueba-micro-update',
  templateUrl: './prueba-micro-update.component.html',
})
export class PruebaMicroUpdateComponent implements OnInit {
  isSaving = false;
  areas: IArea[] = [];
  cultivos: ICultivo[] = [];
  superficies: ISuperficie[] = [];
  userextras: IUserExtra[] = [];
  personals: IPersonal[] = [];

  editForm = this.fb.group({
    id: [],
    tipodeMuestra: [null, [Validators.required]],
    idCatalogo: [null, [Validators.required, Validators.maxLength(10)]],
    lote: [null, [Validators.maxLength(45)]],
    inicio: [null, [Validators.required]],
    fin: [],
    resultado: [],
    unidades: [],
    observaciones: [null, [Validators.maxLength(100)]],
    area: [null, Validators.required],
    cultivo: [],
    superficie: [],
    analista: [null, Validators.required],
    proveedor: [null, Validators.required],
  });

  constructor(
    protected pruebaMicroService: PruebaMicroService,
    protected areaService: AreaService,
    protected cultivoService: CultivoService,
    protected superficieService: SuperficieService,
    protected userExtraService: UserExtraService,
    protected personalService: PersonalService,
    protected activatedRoute: ActivatedRoute,
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

      this.areaService.query().subscribe((res: HttpResponse<IArea[]>) => (this.areas = res.body || []));

      this.cultivoService.query().subscribe((res: HttpResponse<ICultivo[]>) => (this.cultivos = res.body || []));

      this.superficieService.query().subscribe((res: HttpResponse<ISuperficie[]>) => (this.superficies = res.body || []));

      this.userExtraService.query().subscribe((res: HttpResponse<IUserExtra[]>) => (this.userextras = res.body || []));

      this.personalService.query().subscribe((res: HttpResponse<IPersonal[]>) => (this.personals = res.body || []));
    });
  }

  updateForm(pruebaMicro: IPruebaMicro): void {
    this.editForm.patchValue({
      id: pruebaMicro.id,
      tipodeMuestra: pruebaMicro.tipodeMuestra,
      idCatalogo: pruebaMicro.idCatalogo,
      lote: pruebaMicro.lote,
      inicio: pruebaMicro.inicio ? pruebaMicro.inicio.format(DATE_TIME_FORMAT) : null,
      fin: pruebaMicro.fin ? pruebaMicro.fin.format(DATE_TIME_FORMAT) : null,
      resultado: pruebaMicro.resultado,
      unidades: pruebaMicro.unidades,
      observaciones: pruebaMicro.observaciones,
      area: pruebaMicro.area,
      cultivo: pruebaMicro.cultivo,
      superficie: pruebaMicro.superficie,
      analista: pruebaMicro.analista,
      proveedor: pruebaMicro.proveedor,
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
      idCatalogo: this.editForm.get(['idCatalogo'])!.value,
      lote: this.editForm.get(['lote'])!.value,
      inicio: this.editForm.get(['inicio'])!.value ? moment(this.editForm.get(['inicio'])!.value, DATE_TIME_FORMAT) : undefined,
      fin: this.editForm.get(['fin'])!.value ? moment(this.editForm.get(['fin'])!.value, DATE_TIME_FORMAT) : undefined,
      resultado: this.editForm.get(['resultado'])!.value,
      unidades: this.editForm.get(['unidades'])!.value,
      observaciones: this.editForm.get(['observaciones'])!.value,
      area: this.editForm.get(['area'])!.value,
      cultivo: this.editForm.get(['cultivo'])!.value,
      superficie: this.editForm.get(['superficie'])!.value,
      analista: this.editForm.get(['analista'])!.value,
      proveedor: this.editForm.get(['proveedor'])!.value,
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
}
