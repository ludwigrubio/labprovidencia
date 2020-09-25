export interface ITipoProducto {
  id?: number;
  tipo?: string;
}

export class TipoProducto implements ITipoProducto {
  constructor(public id?: number, public tipo?: string) {}
}
