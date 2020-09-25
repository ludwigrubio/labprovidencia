export interface ISuperficie {
  id?: number;
  superficie?: string;
  descripcion?: string;
}

export class Superficie implements ISuperficie {
  constructor(public id?: number, public superficie?: string, public descripcion?: string) {}
}
