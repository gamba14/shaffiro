export const enum TipoDispositivo {
    SENSOR = 'SENSOR',
    ACTUADOR = 'ACTUADOR'
}

export interface IDispositivo {
    id?: number;
    nombre?: string;
    tipo?: TipoDispositivo;
    activo?: boolean;
    configuracion?: string;
    reglaId?: number;
}

export class Dispositivo implements IDispositivo {
    constructor(
        public id?: number,
        public nombre?: string,
        public tipo?: TipoDispositivo,
        public activo?: boolean,
        public configuracion?: string,
        public reglaId?: number
    ) {
        this.activo = this.activo || false;
    }
}
