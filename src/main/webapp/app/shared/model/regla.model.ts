export interface IRegla {
    id?: number;
    nombre?: string;
    logica?: string;
    dispositivosAsociados?: string;
}

export class Regla implements IRegla {
    constructor(public id?: number, public nombre?: string, public logica?: string, public dispositivosAsociados?: string) {}
}
