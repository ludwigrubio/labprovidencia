import { Moment } from 'moment';
import { IPersonal } from 'app/shared/model/personal.model';

export interface IRecepcion {
  id?: number;
  litros?: number;
  tiempo?: Moment;
  turno?: string;
  incentivoLT?: number;
  incentivoT?: number;
  tipoLeche?: string;
  flete?: string;
  proveedor?: IPersonal;
}

export class Recepcion implements IRecepcion {
  constructor(
    public id?: number,
    public litros?: number,
    public tiempo?: Moment,
    public turno?: string,
    public incentivoLT?: number,
    public incentivoT?: number,
    public tipoLeche?: string,
    public flete?: string,
    public proveedor?: IPersonal
  ) {}
}
