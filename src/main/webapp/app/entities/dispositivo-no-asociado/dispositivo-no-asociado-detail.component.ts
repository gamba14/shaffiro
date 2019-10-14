import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDispositivoNoAsociado } from 'app/shared/model/dispositivo-no-asociado.model';

@Component({
    selector: 'jhi-dispositivo-no-asociado-detail',
    templateUrl: './dispositivo-no-asociado-detail.component.html'
})
export class DispositivoNoAsociadoDetailComponent implements OnInit {
    dispositivoNoAsociado: IDispositivoNoAsociado;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dispositivoNoAsociado }) => {
            this.dispositivoNoAsociado = dispositivoNoAsociado;
        });
    }

    previousState() {
        window.history.back();
    }
}
