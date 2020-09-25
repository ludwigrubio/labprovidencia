export interface ICultivo {
  id?: number;
  cultivo?: string;
}

export class Cultivo implements ICultivo {
  constructor(public id?: number, public cultivo?: string) {}
}
