import { Moment } from 'moment';
import { IArea } from 'app/shared/model/area.model';
import { ICultivo } from 'app/shared/model/cultivo.model';
import { ISuperficie } from 'app/shared/model/superficie.model';
import { IProducto } from 'app/shared/model/producto.model';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { IPersonal } from 'app/shared/model/personal.model';

export interface IPruebaMicro {
  id?: number;
  tipodeMuestra?: number;
  lote?: string;
  inicio?: Moment;
  fin?: Moment;
  resultado?: number;
  unidades?: number;
  observaciones?: string;
  area?: IArea;
  cultivo?: ICultivo;
  superficie?: ISuperficie;
  producto?: IProducto;
  analista?: IUserExtra;
  proveedor?: IPersonal;
}

export class PruebaMicro implements IPruebaMicro {
  constructor(
    public id?: number,
    public tipodeMuestra?: number,
    public lote?: string,
    public inicio?: Moment,
    public fin?: Moment,
    public resultado?: number,
    public unidades?: number,
    public observaciones?: string,
    public area?: IArea,
    public cultivo?: ICultivo,
    public superficie?: ISuperficie,
    public producto?: IProducto,
    public analista?: IUserExtra,
    public proveedor?: IPersonal
  ) {}
}
