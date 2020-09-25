import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserExtra, UserExtra } from 'app/shared/model/user-extra.model';
import { UserExtraService } from './user-extra.service';
import { IArea } from 'app/shared/model/area.model';
import { AreaService } from 'app/entities/area/area.service';
import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from 'app/entities/personal/personal.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IArea | IPersonal | IUser;

@Component({
  selector: 'jhi-user-extra-update',
  templateUrl: './user-extra-update.component.html',
})
export class UserExtraUpdateComponent implements OnInit {
  isSaving = false;
  areas: IArea[] = [];
  personals: IPersonal[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    nombreCompleto: [null, [Validators.maxLength(200)]],
    area: [null, Validators.required],
    personal: [null, Validators.required],
    user: [null, Validators.required],
  });

  constructor(
    protected userExtraService: UserExtraService,
    protected areaService: AreaService,
    protected personalService: PersonalService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userExtra }) => {
      this.updateForm(userExtra);

      this.areaService.query().subscribe((res: HttpResponse<IArea[]>) => (this.areas = res.body || []));

      this.personalService.query().subscribe((res: HttpResponse<IPersonal[]>) => (this.personals = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(userExtra: IUserExtra): void {
    this.editForm.patchValue({
      id: userExtra.id,
      nombreCompleto: userExtra.nombreCompleto,
      area: userExtra.area,
      personal: userExtra.personal,
      user: userExtra.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userExtra = this.createFromForm();
    if (userExtra.id !== undefined) {
      this.subscribeToSaveResponse(this.userExtraService.update(userExtra));
    } else {
      this.subscribeToSaveResponse(this.userExtraService.create(userExtra));
    }
  }

  private createFromForm(): IUserExtra {
    return {
      ...new UserExtra(),
      id: this.editForm.get(['id'])!.value,
      nombreCompleto: this.editForm.get(['nombreCompleto'])!.value,
      area: this.editForm.get(['area'])!.value,
      personal: this.editForm.get(['personal'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserExtra>>): void {
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
