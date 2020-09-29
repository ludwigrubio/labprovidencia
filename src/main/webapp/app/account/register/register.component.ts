import { Component, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { JhiLanguageService } from 'ng-jhipster';

import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared/constants/error.constants';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { RegisterService } from './register.service';
import { UserService } from 'app/core/user/user.service';

import { IArea } from 'app/shared/model/area.model';
import { AreaService } from 'app/entities/area/area.service';
import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from 'app/entities/personal/personal.service';

import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { distinctUntilChanged } from 'rxjs/operators';
import { switchMap } from 'rxjs/operators';
import { map } from 'rxjs/operators';

@Component({
  selector: 'jhi-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent implements AfterViewInit {
  @ViewChild('login', { static: false })
  login?: ElementRef;
  authorities: string[] = [];
  isSaving = false;

  doNotMatch = false;
  error = false;
  errorEmailExists = false;
  errorUserExists = false;

  registerForm = this.fb.group({
    login: [
      '',
      [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    ],
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    firstName: ['', [Validators.maxLength(50)]],
    lastName: ['', [Validators.maxLength(50)]],
    authorities: [],
    area: [null, Validators.required],
    personal: [null, Validators.required],
  });

  constructor(
    private languageService: JhiLanguageService,
    private loginModalService: LoginModalService,
    private registerService: RegisterService,
    protected areaService: AreaService,
    protected personalService: PersonalService,
    private fb: FormBuilder,
    private userService: UserService
  ) {}

  ngAfterViewInit(): void {
    if (this.login) {
      this.login.nativeElement.focus();
    }
    this.userService.authorities().subscribe(authorities => {
      this.authorities = authorities;
    });
    this.registerForm.controls['personal'].valueChanges.subscribe(value => {
      if (value) {
        this.registerForm.controls['email'].setValue(value['email']);
        this.registerForm.controls['firstName'].setValue(value['nombre']);
        this.registerForm.controls['lastName'].setValue(value['apellido1'] + ' ' + value['apellido2']);
      } else {
        this.registerForm.controls['email'].setValue('');
        this.registerForm.controls['firstName'].setValue('');
        this.registerForm.controls['lastName'].setValue('');
      }
    });
  }

  register(): void {
    this.doNotMatch = false;
    this.error = false;
    this.errorEmailExists = false;
    this.errorUserExists = false;

    const password = this.registerForm.get(['password'])!.value;
    if (password !== this.registerForm.get(['confirmPassword'])!.value) {
      this.doNotMatch = true;
    } else {
      const login = this.registerForm.get(['login'])!.value;
      const email = this.registerForm.get(['email'])!.value;
      const firstName = this.registerForm.get(['firstName'])!.value;
      const lastName = this.registerForm.get(['lastName'])!.value;
      const authorities = this.registerForm.get(['authorities'])!.value;
      const area = this.registerForm.get(['area'])!.value;
      const personal = this.registerForm.get(['personal'])!.value;

      this.registerService
        .save({
          login,
          email,
          password,
          langKey: this.languageService.getCurrentLanguage(),
          activated: true,
          firstName,
          authorities,
          lastName,
          area: area.id,
          personal: personal.id,
        })
        .subscribe(
          () => this.onSaveSuccess(),
          response => this.processError(response)
        );
    }
  }

  previousState(): void {
    window.history.back();
  }

  openLogin(): void {
    this.loginModalService.open();
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  formatterArea = (x: { area: string }) => x.area;

  searchArea = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.areaService.query({ 'area.contains': term }))),
      map((res: HttpResponse<IArea[]>) => res.body || [])
    );

  formatterPersonal = (x: { nombre: string; apellido1: string; apellido2: string }) => x.nombre + ' ' + x.apellido1 + ' ' + x.apellido2;

  searchPersonal = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.personalService.query({ 'nombre.contains': term }))),
      map((res: HttpResponse<IPersonal[]>) => res.body || [])
    );

  private processError(response: HttpErrorResponse): void {
    if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
      this.errorUserExists = true;
    } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
      this.errorEmailExists = true;
    } else {
      this.error = true;
    }
  }
}
