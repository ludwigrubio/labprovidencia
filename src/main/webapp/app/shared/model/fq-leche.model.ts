import { Moment } from 'moment';
import { IArea } from 'app/shared/model/area.model';
import { IRecepcion } from 'app/shared/model/recepcion.model';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { IPersonal } from 'app/shared/model/personal.model';

export interface IFQLeche {
  id?: number;
  fecha?: Moment;
  lote?: string;
  acidez?: number;
  temperatura?: number;
  agua?: number;
  crioscopia?: number;
  antibiotico?: number;
  delvo?: number;
  grasa?: number;
  solidos?: string;
  densidad?: number;
  lactosa?: number;
  proteina?: number;
  neutralizantes?: number;
  adulterantes?: number;
  reductasa?: number;
  fosfatasa?: number;
  ph?: number;
  observaciones?: string;
  area?: IArea;
  recepcion?: IRecepcion;
  analista?: IUserExtra;
  proveedor?: IPersonal;
}

export class FQLeche implements IFQLeche {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public lote?: string,
    public acidez?: number,
    public temperatura?: number,
    public agua?: number,
    public crioscopia?: number,
    public antibiotico?: number,
    public delvo?: number,
    public grasa?: number,
    public solidos?: string,
    public densidad?: number,
    public lactosa?: number,
    public proteina?: number,
    public neutralizantes?: number,
    public adulterantes?: number,
    public reductasa?: number,
    public fosfatasa?: number,
    public ph?: number,
    public observaciones?: string,
    public area?: IArea,
    public recepcion?: IRecepcion,
    public analista?: IUserExtra,
    public proveedor?: IPersonal
  ) {}
}
