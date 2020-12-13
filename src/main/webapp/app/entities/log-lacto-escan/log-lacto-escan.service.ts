import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILogLactoEscan } from 'app/shared/model/log-lacto-escan.model';

type EntityResponseType = HttpResponse<ILogLactoEscan>;
type EntityArrayResponseType = HttpResponse<ILogLactoEscan[]>;

@Injectable({ providedIn: 'root' })
export class LogLactoEscanService {
  public resourceUrl = SERVER_API_URL + 'api/log-lacto-escans';

  constructor(protected http: HttpClient) {}

  create(logLactoEscan: ILogLactoEscan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logLactoEscan);
    return this.http
      .post<ILogLactoEscan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(logLactoEscan: ILogLactoEscan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logLactoEscan);
    return this.http
      .put<ILogLactoEscan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILogLactoEscan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILogLactoEscan[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(logLactoEscan: ILogLactoEscan): ILogLactoEscan {
    const copy: ILogLactoEscan = Object.assign({}, logLactoEscan, {
      fecha: logLactoEscan.fecha && logLactoEscan.fecha.isValid() ? logLactoEscan.fecha.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((logLactoEscan: ILogLactoEscan) => {
        logLactoEscan.fecha = logLactoEscan.fecha ? moment(logLactoEscan.fecha) : undefined;
      });
    }
    return res;
  }
}
