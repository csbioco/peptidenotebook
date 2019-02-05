import { IProtocol } from 'app/shared/model/protocol.model';
import { ISensor } from 'app/shared/model/sensor.model';
import { IReagent } from 'app/shared/model/reagent.model';

export interface IProtocolentry {
    id?: number;
    ordernumber?: string;
    protocol?: IProtocol;
    sensor?: ISensor;
    reagent?: IReagent;
}

export class Protocolentry implements IProtocolentry {
    constructor(
        public id?: number,
        public ordernumber?: string,
        public protocol?: IProtocol,
        public sensor?: ISensor,
        public reagent?: IReagent
    ) {}
}
