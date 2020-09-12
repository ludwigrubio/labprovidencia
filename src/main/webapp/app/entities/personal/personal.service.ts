import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPersonal } from 'app/shared/model/personal.model';

type EntityResponseType = HttpResponse<IPersonal>;
type EntityArrayResponseType = HttpResponse<IPersonal[]>;

@Injectable({ providedIn: 'root' })
export class PersonalService {
  public resourceUrl = SERVER_API_URL + 'api/personals';

  constructor(protected http: HttpClient) {}

  create(personal: IPersonal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personal);
    return this.http
      .post<IPersonal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(personal: IPersonal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personal);
    return this.http
      .put<IPersonal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPersonal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPersonal[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(personal: IPersonal): IPersonal {
    const copy: IPersonal = Object.assign({}, personal, {
      inicio: personal.inicio && personal.inicio.isValid() ? personal.inicio.toJSON() : undefined,
      fin: personal.fin && personal.fin.isValid() ? personal.fin.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inicio = res.body.inicio ? moment(res.body.inicio) : undefined;
      res.body.fin = res.body.fin ? moment(res.body.fin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((personal: IPersonal) => {
        personal.inicio = personal.inicio ? moment(personal.inicio) : undefined;
        personal.fin = personal.fin ? moment(personal.fin) : undefined;
      });
    }
    return res;
  }
}
