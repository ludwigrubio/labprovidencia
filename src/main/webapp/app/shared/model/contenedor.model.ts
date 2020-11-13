export interface IContenedor {
  id?: number;
  contenedor?: string;
}

export class Contenedor implements IContenedor {
  constructor(public id?: number, public contenedor?: string) {}
}
