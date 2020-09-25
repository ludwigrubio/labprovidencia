import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICultivo } from 'app/shared/model/cultivo.model';

type EntityResponseType = HttpResponse<ICultivo>;
type EntityArrayResponseType = HttpResponse<ICultivo[]>;

@Injectable({ providedIn: 'root' })
export class CultivoService {
  public resourceUrl = SERVER_API_URL + 'api/cultivos';

  constructor(protected http: HttpClient) {}

  create(cultivo: ICultivo): Observable<EntityResponseType> {
    return this.http.post<ICultivo>(this.resourceUrl, cultivo, { observe: 'response' });
  }

  update(cultivo: ICultivo): Observable<EntityResponseType> {
    return this.http.put<ICultivo>(this.resourceUrl, cultivo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICultivo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICultivo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
