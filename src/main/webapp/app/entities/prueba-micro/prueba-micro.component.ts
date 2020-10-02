import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPruebaMicro } from 'app/shared/model/prueba-micro.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PruebaMicroService } from './prueba-micro.service';
import { PruebaMicroDeleteDialogComponent } from './prueba-micro-delete-dialog.component';

// Filter added
import { IArea } from 'app/shared/model/area.model';
import { AreaService } from 'app/entities/area/area.service';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/user-extra.service';
import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from 'app/entities/personal/personal.service';
import { ICultivo } from 'app/shared/model/cultivo.model';
import { CultivoService } from 'app/entities/cultivo/cultivo.service';
import { ISuperficie } from 'app/shared/model/superficie.model';
import { SuperficieService } from 'app/entities/superficie/superficie.service';
import { Observable } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { distinctUntilChanged } from 'rxjs/operators';
import { switchMap } from 'rxjs/operators';
import { map } from 'rxjs/operators';

import * as moment from 'moment'; // add this 1 of 4
@Component({
  selector: 'jhi-prueba-micro',
  templateUrl: './prueba-micro.component.html',
})
export class PruebaMicroComponent implements OnInit, OnDestroy {
  pruebaMicros?: IPruebaMicro[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  filters = {
    lote: '',
    tipodeMuestra: '',
    area: { id: '' },
    analista: { id: '' },
    proveedor: { id: '' },
    cultivo: { id: '' },
    superficie: { id: '' },
    fechaInicio: '',
    fechaFin: '',
  };
  dateTop!: string;
  dateButton!: string;

  constructor(
    protected pruebaMicroService: PruebaMicroService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected areaService: AreaService,
    protected userExtraService: UserExtraService,
    protected personalService: PersonalService,
    protected cultivoService: CultivoService,
    protected superficieService: SuperficieService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;
    const isValidDateI = moment(this.filters.fechaInicio, 'YYYY-MM-DD', true).isValid();
    const isValidDateF = moment(this.filters.fechaFin, 'YYYY-MM-DD', true).isValid();

    if (this.filters.fechaInicio !== '' && isValidDateI) {
      this.dateTop = new Date(this.filters.fechaInicio + ' 00:00:00').toISOString();
    } else {
      this.dateTop = '';
    }

    if (this.filters.fechaFin !== '' && isValidDateF) {
      this.dateButton = new Date(this.filters.fechaFin + ' 23:59:59').toISOString();
    } else {
      this.dateButton = '';
    }

    this.pruebaMicroService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        'lote.contains': this.filters.lote || '',
        'tipodeMuestra.equals': this.filters.tipodeMuestra || '',
        'areaId.equals': this.filters.area.id || '',
        'analistaId.equals': this.filters.analista.id || '',
        'proveedorId.equals': this.filters.proveedor.id || '',
        'cultivoId.equals': this.filters.cultivo.id || '',
        'superficieId.equals': this.filters.superficie.id || '',
        'inicio.greaterThanOrEqual': this.dateTop || '',
        'fin.lessThanOrEqual': this.dateButton || '',
      })
      .subscribe(
        (res: HttpResponse<IPruebaMicro[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInPruebaMicros();
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPruebaMicro): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPruebaMicros(): void {
    this.eventSubscriber = this.eventManager.subscribe('pruebaMicroListModification', () => this.loadPage());
  }

  delete(pruebaMicro: IPruebaMicro): void {
    const modalRef = this.modalService.open(PruebaMicroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pruebaMicro = pruebaMicro;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IPruebaMicro[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/prueba-micro'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.pruebaMicros = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
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

  formatterCultivo = (x: { cultivo: string }) => x.cultivo;

  searchCultivo = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.cultivoService.query({ 'cultivo.contains': term }))),
      map((res: HttpResponse<ICultivo[]>) => res.body || [])
    );

  formatterSuperficie = (x: { superficie: string }) => x.superficie;

  searchSuperficie = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.superficieService.query({ 'superficie.contains': term }))),
      map((res: HttpResponse<ISuperficie[]>) => res.body || [])
    );
}
