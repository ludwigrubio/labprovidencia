import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoProducto } from 'app/shared/model/tipo-producto.model';

type EntityResponseType = HttpResponse<ITipoProducto>;
type EntityArrayResponseType = HttpResponse<ITipoProducto[]>;

@Injectable({ providedIn: 'root' })
export class TipoProductoService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-productos';

  constructor(protected http: HttpClient) {}

  create(tipoProducto: ITipoProducto): Observable<EntityResponseType> {
    return this.http.post<ITipoProducto>(this.resourceUrl, tipoProducto, { observe: 'response' });
  }

  update(tipoProducto: ITipoProducto): Observable<EntityResponseType> {
    return this.http.put<ITipoProducto>(this.resourceUrl, tipoProducto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoProducto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoProducto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
