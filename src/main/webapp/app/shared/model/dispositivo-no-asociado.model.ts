export interface IDispositivoNoAsociado {
    id?: number;
    mac?: string;
    uuid?: string;
}

export class DispositivoNoAsociado implements IDispositivoNoAsociado {
    constructor(public id?: number, public mac?: string, public uuid?: string) {}
}
