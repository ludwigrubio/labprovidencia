import { Moment } from 'moment';

export interface ILogLactoEscan {
  id?: number;
  tipo?: number;
  fecha?: Moment;
  nombreArchivo?: string;
  numeroFila?: number;
  mensajeError?: string;
}

export class LogLactoEscan implements ILogLactoEscan {
  constructor(
    public id?: number,
    public tipo?: number,
    public fecha?: Moment,
    public nombreArchivo?: string,
    public numeroFila?: number,
    public mensajeError?: string
  ) {}
}
