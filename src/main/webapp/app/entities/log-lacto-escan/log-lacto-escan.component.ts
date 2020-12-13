import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILogLactoEscan } from 'app/shared/model/log-lacto-escan.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LogLactoEscanService } from './log-lacto-escan.service';
import { LogLactoEscanDeleteDialogComponent } from './log-lacto-escan-delete-dialog.component';

@Component({
  selector: 'jhi-log-lacto-escan',
  templateUrl: './log-lacto-escan.component.html',
})
export class LogLactoEscanComponent implements OnInit, OnDestroy {
  logLactoEscans?: ILogLactoEscan[];
  eventSubscriber?: Subscription;
  totalItems = 30;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  filters = { fecha: '', nombreArchivo: '' };
  dateTop!: string;
  dateButton!: string;

  constructor(
    protected logLactoEscanService: LogLactoEscanService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    if (this.filters.fecha !== '') {
      this.dateTop = new Date(this.filters.fecha + ' 00:00:00').toISOString();
      this.dateButton = new Date(this.filters.fecha + ' 23:59:59').toISOString();
    } else {
      this.dateTop = '';
      this.dateButton = '';
    }
    this.logLactoEscanService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        'nombreArchivo.contains': this.filters.nombreArchivo || '',
        'fecha.greaterThanOrEqual': this.dateTop || '',
        'fecha.lessThanOrEqual': this.dateButton || '',
      })
      .subscribe(
        (res: HttpResponse<ILogLactoEscan[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInLogLactoEscans();
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'desc';
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

  trackId(index: number, item: ILogLactoEscan): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLogLactoEscans(): void {
    this.eventSubscriber = this.eventManager.subscribe('logLactoEscanListModification', () => this.loadPage());
  }

  delete(logLactoEscan: ILogLactoEscan): void {
    const modalRef = this.modalService.open(LogLactoEscanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.logLactoEscan = logLactoEscan;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('fecha');
    }
    return result;
  }

  protected onSuccess(data: ILogLactoEscan[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/log-lacto-escan'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.logLactoEscans = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
