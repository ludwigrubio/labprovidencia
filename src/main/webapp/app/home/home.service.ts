import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
// import { map } from 'rxjs/operators';
// import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
// import { createRequestOption } from 'app/shared/util/request-util';
// import { IFQLeche } from 'app/shared/model/fq-leche.model';

type EntityResponseType = HttpResponse<String>;

@Injectable({ providedIn: 'root' })
export class HomeService {
  public resourceUrl = SERVER_API_URL + 'api/tinas/creacion';

  constructor(protected http: HttpClient) {}

  createByTinas(count: number): Observable<EntityResponseType> {
    return this.http.get<String>(`${this.resourceUrl}/${count}`, { observe: 'response' });
  }
}
