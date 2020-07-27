export interface IDispositivoNoAsociado {
    id?: number;
    mac?: string;
    uuid?: string;
    puerto?: number;
}

export class DispositivoNoAsociado implements IDispositivoNoAsociado {
    constructor(public id?: number, public mac?: string, public uuid?: string, public puerto?: number) {}
}
