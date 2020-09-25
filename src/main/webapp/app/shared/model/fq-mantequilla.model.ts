import { Moment } from 'moment';
import { IArea } from 'app/shared/model/area.model';
import { IProducto } from 'app/shared/model/producto.model';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { IPersonal } from 'app/shared/model/personal.model';

export interface IFQMantequilla {
  id?: number;
  fecha?: Moment;
  lote?: string;
  ph?: number;
  humedad?: number;
  observaciones?: string;
  area?: IArea;
  producto?: IProducto;
  analista?: IUserExtra;
  proveedor?: IPersonal;
}

export class FQMantequilla implements IFQMantequilla {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public lote?: string,
    public ph?: number,
    public humedad?: number,
    public observaciones?: string,
    public area?: IArea,
    public producto?: IProducto,
    public analista?: IUserExtra,
    public proveedor?: IPersonal
  ) {}
}
