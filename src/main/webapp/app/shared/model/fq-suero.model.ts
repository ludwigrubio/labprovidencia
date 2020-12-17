import { Moment } from 'moment';
import { IArea } from 'app/shared/model/area.model';
import { IProducto } from 'app/shared/model/producto.model';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { IPersonal } from 'app/shared/model/personal.model';
import { IContenedor } from 'app/shared/model/contenedor.model';
import { IProceso } from 'app/shared/model/proceso.model';

export interface IFQSuero {
  id?: number;
  fecha?: Moment;
  lote?: string;
  acidez?: number;
  temperatura?: number;
  delvo?: number;
  solidos?: number;
  neutralizantes?: number;
  ph?: number;
  cloro?: number;
  almidon?: number;
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
  proceso?: IProceso;
}

export class FQSuero implements IFQSuero {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public lote?: string,
    public acidez?: number,
    public temperatura?: number,
    public delvo?: number,
    public solidos?: number,
    public neutralizantes?: number,
    public ph?: number,
    public cloro?: number,
    public almidon?: number,
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
    public contenedor?: IContenedor,
    public proceso?: IProceso
  ) {}
}
