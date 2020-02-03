import { IDispositivo } from 'app/shared/model/dispositivo.model';

export interface IRegla {
    id?: number;
    nombre?: string;
    antecedente?: string;
    concecuente?: string;
    operador?: string;
    dispositivos?: IDispositivo[];
}

export class Regla implements IRegla {
    constructor(
        public id?: number,
        public nombre?: string,
        public antecedente?: string,
        public concecuente?: string,
        public operador?: string,
        public dispositivos?: IDispositivo[]
    ) {}
}
