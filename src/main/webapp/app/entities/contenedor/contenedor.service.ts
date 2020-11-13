import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContenedor } from 'app/shared/model/contenedor.model';

type EntityResponseType = HttpResponse<IContenedor>;
type EntityArrayResponseType = HttpResponse<IContenedor[]>;

@Injectable({ providedIn: 'root' })
export class ContenedorService {
  public resourceUrl = SERVER_API_URL + 'api/contenedors';

  constructor(protected http: HttpClient) {}

  create(contenedor: IContenedor): Observable<EntityResponseType> {
    return this.http.post<IContenedor>(this.resourceUrl, contenedor, { observe: 'response' });
  }

  update(contenedor: IContenedor): Observable<EntityResponseType> {
    return this.http.put<IContenedor>(this.resourceUrl, contenedor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContenedor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContenedor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
