import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFQQueso } from 'app/shared/model/fq-queso.model';

type EntityResponseType = HttpResponse<IFQQueso>;
type EntityArrayResponseType = HttpResponse<IFQQueso[]>;

@Injectable({ providedIn: 'root' })
export class FQQuesoService {
  public resourceUrl = SERVER_API_URL + 'api/fq-quesos';

  constructor(protected http: HttpClient) {}

  create(fQQueso: IFQQueso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fQQueso);
    return this.http
      .post<IFQQueso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fQQueso: IFQQueso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fQQueso);
    return this.http
      .put<IFQQueso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFQQueso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFQQueso[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fQQueso: IFQQueso): IFQQueso {
    const copy: IFQQueso = Object.assign({}, fQQueso, {
      fecha: fQQueso.fecha && fQQueso.fecha.isValid() ? fQQueso.fecha.toJSON() : undefined,
      caducidad: fQQueso.caducidad && fQQueso.caducidad.isValid() ? fQQueso.caducidad.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
      res.body.caducidad = res.body.caducidad ? moment(res.body.caducidad) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fQQueso: IFQQueso) => {
        fQQueso.fecha = fQQueso.fecha ? moment(fQQueso.fecha) : undefined;
        fQQueso.caducidad = fQQueso.caducidad ? moment(fQQueso.caducidad) : undefined;
      });
    }
    return res;
  }
}
