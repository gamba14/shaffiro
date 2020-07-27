import { IRegla } from 'app/shared/model/regla.model';

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
    puerto?: number;
    reglas?: IRegla[];
}

export class Dispositivo implements IDispositivo {
    constructor(
        public id?: number,
        public nombre?: string,
        public tipo?: TipoDispositivo,
        public activo?: boolean,
        public configuracion?: string,
        public puerto?: number,
        public reglas?: IRegla[]
    ) {
        this.activo = this.activo || false;
    }
}
