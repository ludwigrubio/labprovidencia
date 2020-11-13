import { Moment } from 'moment';
import { IArea } from 'app/shared/model/area.model';
import { IProducto } from 'app/shared/model/producto.model';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { IPersonal } from 'app/shared/model/personal.model';
import { IContenedor } from 'app/shared/model/contenedor.model';

export interface IFQCrema {
  id?: number;
  fecha?: Moment;
  lote?: string;
  acidez?: number;
  grasa?: number;
  ph?: number;
  dummy1?: number;
  dummy2?: number;
  dummy3?: number;
  dummy4?: number;
  dummy5?: number;
  observaciones?: string;
  area?: IArea;
  producto?: IProducto;
  analista?: IUserExtra;
  proveedor?: IPersonal;
  contenedor?: IContenedor;
}

export class FQCrema implements IFQCrema {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public lote?: string,
    public acidez?: number,
    public grasa?: number,
    public ph?: number,
    public dummy1?: number,
    public dummy2?: number,
    public dummy3?: number,
    public dummy4?: number,
    public dummy5?: number,
    public observaciones?: string,
    public area?: IArea,
    public producto?: IProducto,
    public analista?: IUserExtra,
    public proveedor?: IPersonal,
    public contenedor?: IContenedor
  ) {}
}
