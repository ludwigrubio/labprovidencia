import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFQLeche } from 'app/shared/model/fq-leche.model';

type EntityResponseType = HttpResponse<IFQLeche>;
type EntityArrayResponseType = HttpResponse<IFQLeche[]>;

@Injectable({ providedIn: 'root' })
export class FQLecheService {
  public resourceUrl = SERVER_API_URL + 'api/fq-leches';

  constructor(protected http: HttpClient) {}

  create(fQLeche: IFQLeche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fQLeche);
    return this.http
      .post<IFQLeche>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fQLeche: IFQLeche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fQLeche);
    return this.http
      .put<IFQLeche>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFQLeche>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFQLeche[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fQLeche: IFQLeche): IFQLeche {
    const copy: IFQLeche = Object.assign({}, fQLeche, {
      fecha: fQLeche.fecha && fQLeche.fecha.isValid() ? fQLeche.fecha.toJSON() : undefined,
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
      res.body.forEach((fQLeche: IFQLeche) => {
        fQLeche.fecha = fQLeche.fecha ? moment(fQLeche.fecha) : undefined;
      });
    }
    return res;
  }
}
