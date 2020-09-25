import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPruebaMicro } from 'app/shared/model/prueba-micro.model';

type EntityResponseType = HttpResponse<IPruebaMicro>;
type EntityArrayResponseType = HttpResponse<IPruebaMicro[]>;

@Injectable({ providedIn: 'root' })
export class PruebaMicroService {
  public resourceUrl = SERVER_API_URL + 'api/prueba-micros';

  constructor(protected http: HttpClient) {}

  create(pruebaMicro: IPruebaMicro): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pruebaMicro);
    return this.http
      .post<IPruebaMicro>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pruebaMicro: IPruebaMicro): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pruebaMicro);
    return this.http
      .put<IPruebaMicro>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPruebaMicro>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPruebaMicro[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pruebaMicro: IPruebaMicro): IPruebaMicro {
    const copy: IPruebaMicro = Object.assign({}, pruebaMicro, {
      inicio: pruebaMicro.inicio && pruebaMicro.inicio.isValid() ? pruebaMicro.inicio.toJSON() : undefined,
      fin: pruebaMicro.fin && pruebaMicro.fin.isValid() ? pruebaMicro.fin.toJSON() : undefined,
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
      res.body.forEach((pruebaMicro: IPruebaMicro) => {
        pruebaMicro.inicio = pruebaMicro.inicio ? moment(pruebaMicro.inicio) : undefined;
        pruebaMicro.fin = pruebaMicro.fin ? moment(pruebaMicro.fin) : undefined;
      });
    }
    return res;
  }
}
