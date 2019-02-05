import { ICalculated } from 'app/shared/model/calculated.model';

export interface ICalculatedEntry {
    id?: number;
    sequencenumber?: number;
    aa?: string;
    sc?: number;
    dc?: number;
    scale?: number;
    mwwithprotection?: number;
    mwwithoutprotection?: number;
    eachaaweight?: number;
    difficulty?: number;
    currentresinweight?: number;
    protocolname?: string;
    calculated?: ICalculated;
}

export class CalculatedEntry implements ICalculatedEntry {
    constructor(
        public id?: number,
        public sequencenumber?: number,
        public aa?: string,
        public sc?: number,
        public dc?: number,
        public scale?: number,
        public mwwithprotection?: number,
        public mwwithoutprotection?: number,
        public eachaaweight?: number,
        public difficulty?: number,
        public currentresinweight?: number,
        public protocolname?: string,
        public calculated?: ICalculated
    ) {}
}
