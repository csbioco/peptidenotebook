export interface IAas {
    id?: number;
    aaname?: string;
    threeletter?: string;
    mwwithprotection?: number;
    mwwithoutprotection?: number;
    pi?: number;
    unitprice?: number;
    difficulty?: number;
    numc?: number;
    numh?: number;
    numn?: number;
    numo?: number;
    nums?: number;
    solubility?: string;
}

export class Aas implements IAas {
    constructor(
        public id?: number,
        public aaname?: string,
        public threeletter?: string,
        public mwwithprotection?: number,
        public mwwithoutprotection?: number,
        public pi?: number,
        public unitprice?: number,
        public difficulty?: number,
        public numc?: number,
        public numh?: number,
        public numn?: number,
        public numo?: number,
        public nums?: number,
        public solubility?: string
    ) {}
}
