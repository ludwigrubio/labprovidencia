import { ITipoProducto } from 'app/shared/model/tipo-producto.model';

export interface IProducto {
  id?: number;
  producto?: string;
  clave?: string;
  serieEAN?: string;
  tipo?: ITipoProducto;
}

export class Producto implements IProducto {
  constructor(public id?: number, public producto?: string, public clave?: string, public serieEAN?: string, public tipo?: ITipoProducto) {}
}
