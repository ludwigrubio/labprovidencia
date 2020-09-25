import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFQCrema } from 'app/shared/model/fq-crema.model';

type EntityResponseType = HttpResponse<IFQCrema>;
type EntityArrayResponseType = HttpResponse<IFQCrema[]>;

@Injectable({ providedIn: 'root' })
export class FQCremaService {
  public resourceUrl = SERVER_API_URL + 'api/fq-cremas';

  constructor(protected http: HttpClient) {}

  create(fQCrema: IFQCrema): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fQCrema);
    return this.http
      .post<IFQCrema>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fQCrema: IFQCrema): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fQCrema);
    return this.http
      .put<IFQCrema>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFQCrema>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFQCrema[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fQCrema: IFQCrema): IFQCrema {
    const copy: IFQCrema = Object.assign({}, fQCrema, {
      fecha: fQCrema.fecha && fQCrema.fecha.isValid() ? fQCrema.fecha.toJSON() : undefined,
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
      res.body.forEach((fQCrema: IFQCrema) => {
        fQCrema.fecha = fQCrema.fecha ? moment(fQCrema.fecha) : undefined;
      });
    }
    return res;
  }
}
