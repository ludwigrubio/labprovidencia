import { IArea } from 'app/shared/model/area.model';
import { IPersonal } from 'app/shared/model/personal.model';
import { IUser } from 'app/core/user/user.model';

export interface IUserExtra {
  id?: number;
  nombreCompleto?: string;
  area?: IArea;
  personal?: IPersonal;
  user?: IUser;
}

export class UserExtra implements IUserExtra {
  constructor(public id?: number, public nombreCompleto?: string, public area?: IArea, public personal?: IPersonal, public user?: IUser) {}
}
