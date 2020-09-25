import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFQMantequilla } from 'app/shared/model/fq-mantequilla.model';

type EntityResponseType = HttpResponse<IFQMantequilla>;
type EntityArrayResponseType = HttpResponse<IFQMantequilla[]>;

@Injectable({ providedIn: 'root' })
export class FQMantequillaService {
  public resourceUrl = SERVER_API_URL + 'api/fq-mantequillas';

  constructor(protected http: HttpClient) {}

  create(fQMantequilla: IFQMantequilla): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fQMantequilla);
    return this.http
      .post<IFQMantequilla>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fQMantequilla: IFQMantequilla): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fQMantequilla);
    return this.http
      .put<IFQMantequilla>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFQMantequilla>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFQMantequilla[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fQMantequilla: IFQMantequilla): IFQMantequilla {
    const copy: IFQMantequilla = Object.assign({}, fQMantequilla, {
      fecha: fQMantequilla.fecha && fQMantequilla.fecha.isValid() ? fQMantequilla.fecha.toJSON() : undefined,
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
      res.body.forEach((fQMantequilla: IFQMantequilla) => {
        fQMantequilla.fecha = fQMantequilla.fecha ? moment(fQMantequilla.fecha) : undefined;
      });
    }
    return res;
  }
}
