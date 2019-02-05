import { IUser } from 'app/core/user/user.model';

export interface ISensor {
    id?: number;
    sensorname?: string;
    amount?: number;
    user?: IUser;
}

export class Sensor implements ISensor {
    constructor(public id?: number, public sensorname?: string, public amount?: number, public user?: IUser) {}
}
