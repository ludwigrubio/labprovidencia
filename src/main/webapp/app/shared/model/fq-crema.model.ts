import { Moment } from 'moment';
import { IArea } from 'app/shared/model/area.model';
import { IProducto } from 'app/shared/model/producto.model';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { IPersonal } from 'app/shared/model/personal.model';

export interface IFQCrema {
  id?: number;
  fecha?: Moment;
  lote?: string;
  acidez?: number;
  grasa?: number;
  observaciones?: string;
  area?: IArea;
  producto?: IProducto;
  analista?: IUserExtra;
  proveedor?: IPersonal;
}

export class FQCrema implements IFQCrema {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public lote?: string,
    public acidez?: number,
    public grasa?: number,
    public observaciones?: string,
    public area?: IArea,
    public producto?: IProducto,
    public analista?: IUserExtra,
    public proveedor?: IPersonal
  ) {}
}
