import { Moment } from 'moment';
import { IPersonal } from 'app/shared/model/personal.model';

export interface IRecepcion {
  id?: number;
  idProveedor?: number;
  litros?: number;
  tiempo?: Moment;
  turno?: string;
  incentivoLT?: number;
  incentivoT?: number;
  proveedor?: IPersonal;
}

export class Recepcion implements IRecepcion {
  constructor(
    public id?: number,
    public idProveedor?: number,
    public litros?: number,
    public tiempo?: Moment,
    public turno?: string,
    public incentivoLT?: number,
    public incentivoT?: number,
    public proveedor?: IPersonal
  ) {}
}
