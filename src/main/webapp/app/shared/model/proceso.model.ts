export interface IProceso {
  id?: number;
  proceso?: string;
}

export class Proceso implements IProceso {
  constructor(public id?: number, public proceso?: string) {}
}
