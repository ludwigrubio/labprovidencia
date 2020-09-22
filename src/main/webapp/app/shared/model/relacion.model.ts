export interface IRelacion {
  id?: number;
  relacion?: string;
}

export class Relacion implements IRelacion {
  constructor(public id?: number, public relacion?: string) {}
}
