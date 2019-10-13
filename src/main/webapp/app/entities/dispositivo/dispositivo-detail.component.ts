import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDispositivo } from 'app/shared/model/dispositivo.model';

@Component({
    selector: 'jhi-dispositivo-detail',
    templateUrl: './dispositivo-detail.component.html'
})
export class DispositivoDetailComponent implements OnInit {
    dispositivo: IDispositivo;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dispositivo }) => {
            this.dispositivo = dispositivo;
        });
    }

    previousState() {
        window.history.back();
    }
}
