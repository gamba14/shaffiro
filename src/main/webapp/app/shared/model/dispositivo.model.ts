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
    regla?: string;
}

export class Dispositivo implements IDispositivo {
    constructor(
        public id?: number,
        public nombre?: string,
        public tipo?: TipoDispositivo,
        public activo?: boolean,
        public configuracion?: string,
        public regla?: string
    ) {
        this.activo = this.activo || false;
    }
}
