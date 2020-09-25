import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISuperficie } from 'app/shared/model/superficie.model';

type EntityResponseType = HttpResponse<ISuperficie>;
type EntityArrayResponseType = HttpResponse<ISuperficie[]>;

@Injectable({ providedIn: 'root' })
export class SuperficieService {
  public resourceUrl = SERVER_API_URL + 'api/superficies';

  constructor(protected http: HttpClient) {}

  create(superficie: ISuperficie): Observable<EntityResponseType> {
    return this.http.post<ISuperficie>(this.resourceUrl, superficie, { observe: 'response' });
  }

  update(superficie: ISuperficie): Observable<EntityResponseType> {
    return this.http.put<ISuperficie>(this.resourceUrl, superficie, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISuperficie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISuperficie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
