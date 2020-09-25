import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFQSuero } from 'app/shared/model/fq-suero.model';

type EntityResponseType = HttpResponse<IFQSuero>;
type EntityArrayResponseType = HttpResponse<IFQSuero[]>;

@Injectable({ providedIn: 'root' })
export class FQSueroService {
  public resourceUrl = SERVER_API_URL + 'api/fq-sueros';

  constructor(protected http: HttpClient) {}

  create(fQSuero: IFQSuero): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fQSuero);
    return this.http
      .post<IFQSuero>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fQSuero: IFQSuero): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fQSuero);
    return this.http
      .put<IFQSuero>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFQSuero>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFQSuero[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fQSuero: IFQSuero): IFQSuero {
    const copy: IFQSuero = Object.assign({}, fQSuero, {
      fecha: fQSuero.fecha && fQSuero.fecha.isValid() ? fQSuero.fecha.toJSON() : undefined,
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
      res.body.forEach((fQSuero: IFQSuero) => {
        fQSuero.fecha = fQSuero.fecha ? moment(fQSuero.fecha) : undefined;
      });
    }
    return res;
  }
}
