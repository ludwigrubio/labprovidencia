import { Moment } from 'moment';
import { IArea } from 'app/shared/model/area.model';
import { IProducto } from 'app/shared/model/producto.model';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { IPersonal } from 'app/shared/model/personal.model';

export interface IFQQueso {
  id?: number;
  fecha?: Moment;
  lote?: string;
  humedad?: number;
  ph?: number;
  fundicion?: number;
  presentacion?: number;
  caducidad?: Moment;
  apariencia?: number;
  sabor?: number;
  color?: number;
  olor?: number;
  textura?: number;
  hilado?: number;
  observaciones?: string;
  area?: IArea;
  producto?: IProducto;
  analista?: IUserExtra;
  proveedor?: IPersonal;
}

export class FQQueso implements IFQQueso {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public lote?: string,
    public humedad?: number,
    public ph?: number,
    public fundicion?: number,
    public presentacion?: number,
    public caducidad?: Moment,
    public apariencia?: number,
    public sabor?: number,
    public color?: number,
    public olor?: number,
    public textura?: number,
    public hilado?: number,
    public observaciones?: string,
    public area?: IArea,
    public producto?: IProducto,
    public analista?: IUserExtra,
    public proveedor?: IPersonal
  ) {}
}
