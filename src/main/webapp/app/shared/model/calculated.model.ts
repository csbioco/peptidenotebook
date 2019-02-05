import { IUser } from 'app/core/user/user.model';

export interface ICalculated {
    id?: number;
    sequence?: string;
    sc?: number;
    dc?: number;
    scale?: number;
    pi?: number;
    netcharge?: number;
    solubility?: string;
    numc?: number;
    numh?: number;
    numn?: number;
    numo?: number;
    nums?: number;
    startresin?: number;
    costresin?: number;
    weightgain?: number;
    totalweight?: number;
    costaa?: number;
    sumeachaaweight?: number;
    resinunitprice?: number;
    substitude?: number;
    bound?: number;
    costwaste?: number;
    protocolname?: string;
    user?: IUser;
}

export class Calculated implements ICalculated {
    constructor(
        public id?: number,
        public sequence?: string,
        public sc?: number,
        public dc?: number,
        public scale?: number,
        public pi?: number,
        public netcharge?: number,
        public solubility?: string,
        public numc?: number,
        public numh?: number,
        public numn?: number,
        public numo?: number,
        public nums?: number,
        public startresin?: number,
        public costresin?: number,
        public weightgain?: number,
        public totalweight?: number,
        public costaa?: number,
        public sumeachaaweight?: number,
        public resinunitprice?: number,
        public substitude?: number,
        public bound?: number,
        public costwaste?: number,
        public protocolname?: string,
        public user?: IUser
    ) {}
}
