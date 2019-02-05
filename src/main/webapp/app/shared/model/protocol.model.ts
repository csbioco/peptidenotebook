import { IUser } from 'app/core/user/user.model';

export interface IProtocol {
    id?: number;
    protocolname?: string;
    user?: IUser;
}

export class Protocol implements IProtocol {
    constructor(public id?: number, public protocolname?: string, public user?: IUser) {}
}
