export interface IArea {
  id?: number;
  area?: string;
  descripcion?: string;
}

export class Area implements IArea {
  constructor(public id?: number, public area?: string, public descripcion?: string) {}
}
