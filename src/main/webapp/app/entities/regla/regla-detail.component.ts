import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegla } from 'app/shared/model/regla.model';

@Component({
    selector: 'jhi-regla-detail',
    templateUrl: './regla-detail.component.html'
})
export class ReglaDetailComponent implements OnInit {
    regla: IRegla;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ regla }) => {
            this.regla = regla;
        });
    }

    previousState() {
        window.history.back();
    }
}
