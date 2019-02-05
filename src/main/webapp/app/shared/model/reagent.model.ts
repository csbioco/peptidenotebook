import { IUser } from 'app/core/user/user.model';

export interface IReagent {
    id?: number;
    reagentname?: string;
    unitprice?: number;
    wasterunitprice?: number;
    user?: IUser;
}

export class Reagent implements IReagent {
    constructor(
        public id?: number,
        public reagentname?: string,
        public unitprice?: number,
        public wasterunitprice?: number,
        public user?: IUser
    ) {}
}
