import { IArea } from 'app/shared/model/area.model';

export interface IDummy {
  id?: number;
  name?: string;
  area?: IArea;
}

export class Dummy implements IDummy {
  constructor(public id?: number, public name?: string, public area?: IArea) {}
}
