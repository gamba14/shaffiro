import { IDispositivo } from 'app/shared/model/dispositivo.model';

export interface IRegla {
    id?: number;
    nombre?: string;
    logica?: string;
    dispositivos?: IDispositivo[];
}

export class Regla implements IRegla {
    constructor(public id?: number, public nombre?: string, public logica?: string, public dispositivos?: IDispositivo[]) {}
}
