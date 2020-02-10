export const enum Unidad {
    CELSIUS = 'CELSIUS',
    AMPERE = 'AMPERE',
    LUMENES = 'LUMENES'
}

export interface IRegla {
    id?: number;
    nombre?: string;
    unidad?: Unidad;
    valor?: string;
    operador?: string;
    dispositivoAsociadoId?: number;
}

export class Regla implements IRegla {
    constructor(
        public id?: number,
        public nombre?: string,
        public unidad?: Unidad,
        public valor?: string,
        public operador?: string,
        public dispositivoAsociadoId?: number
    ) {}
}
