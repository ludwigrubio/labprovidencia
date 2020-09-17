import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDummy, Dummy } from 'app/shared/model/dummy.model';
import { DummyService } from './dummy.service';
import { IArea } from 'app/shared/model/area.model';
import { AreaService } from 'app/entities/area/area.service';

import { debounceTime } from 'rxjs/operators';
import { distinctUntilChanged } from 'rxjs/operators';
import { switchMap } from 'rxjs/operators';
import { map } from 'rxjs/operators';

@Component({
  selector: 'jhi-dummy-update',
  templateUrl: './dummy-update.component.html',
})
export class DummyUpdateComponent implements OnInit {
  isSaving = false;
  areas: IArea[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(45)]],
    area: [null, Validators.required],
  });

  constructor(
    protected dummyService: DummyService,
    protected areaService: AreaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dummy }) => {
      this.updateForm(dummy);

      //   this.areaService.query().subscribe((res: HttpResponse<IArea[]>) => (this.areas = res.body || []));
    });
  }

  updateForm(dummy: IDummy): void {
    this.editForm.patchValue({
      id: dummy.id,
      name: dummy.name,
      area: dummy.area,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dummy = this.createFromForm();
    if (dummy.id !== undefined) {
      this.subscribeToSaveResponse(this.dummyService.update(dummy));
    } else {
      this.subscribeToSaveResponse(this.dummyService.create(dummy));
    }
  }

  private createFromForm(): IDummy {
    return {
      ...new Dummy(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      area: this.editForm.get(['area'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDummy>>): void {
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

  trackById(index: number, item: IArea): any {
    return item.id;
  }
  formatter = (x: { area: string }) => x.area;

  selectedItem(area?: IArea): void {
    this.editForm.patchValue({
      area: area?.id,
    });
  }

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.areaService.query({ 'area.contains': term }))),
      map((res: HttpResponse<IArea[]>) => res.body || [])
    );
}
