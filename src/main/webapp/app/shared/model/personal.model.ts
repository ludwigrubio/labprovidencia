import { Moment } from 'moment';
import { IArea } from 'app/shared/model/area.model';
import { IDummy } from 'app/shared/model/dummy.model';

export interface IPersonal {
  id?: number;
  nombre?: string;
  apellido1?: string;
  apellido2?: string;
  alias?: string;
  domicilio?: string;
  colonia?: string;
  localidad?: string;
  estado?: string;
  pais?: string;
  latitud?: string;
  longitud?: string;
  cp?: number;
  telefono?: string;
  email?: string;
  rfc?: string;
  inicio?: Moment;
  fin?: Moment;
  cargo?: string;
  comentario?: string;
  area?: IArea;
  dummy?: IDummy;
}

export class Personal implements IPersonal {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellido1?: string,
    public apellido2?: string,
    public alias?: string,
    public domicilio?: string,
    public colonia?: string,
    public localidad?: string,
    public estado?: string,
    public pais?: string,
    public latitud?: string,
    public longitud?: string,
    public cp?: number,
    public telefono?: string,
    public email?: string,
    public rfc?: string,
    public inicio?: Moment,
    public fin?: Moment,
    public cargo?: string,
    public comentario?: string,
    public area?: IArea,
    public dummy?: IDummy
  ) {}
}
