import { IPersonal } from 'app/shared/model/personal.model';

export interface IArea {
  id?: number;
  area?: string;
  descripcion?: string;
  personals?: IPersonal[];
}

export class Area implements IArea {
  constructor(public id?: number, public area?: string, public descripcion?: string, public personals?: IPersonal[]) {}
}
