export interface IRegla {
    id?: number;
    nombre?: string;
    antecedente?: string;
    concecuente?: string;
    operador?: string;
    dispositivoAsociadoId?: number;
}

export class Regla implements IRegla {
    constructor(
        public id?: number,
        public nombre?: string,
        public antecedente?: string,
        public concecuente?: string,
        public operador?: string,
        public dispositivoAsociadoId?: number
    ) {}
}
