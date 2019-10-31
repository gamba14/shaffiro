import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IRegla } from 'app/shared/model/regla.model';
import { ReglaService } from './regla.service';

@Component({
    selector: 'jhi-regla-update',
    templateUrl: './regla-update.component.html'
})
export class ReglaUpdateComponent implements OnInit {
    regla: IRegla;
    isSaving: boolean;

    constructor(protected reglaService: ReglaService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ regla }) => {
            this.regla = regla;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.regla.id !== undefined) {
            this.subscribeToSaveResponse(this.reglaService.update(this.regla));
        } else {
            this.subscribeToSaveResponse(this.reglaService.create(this.regla));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegla>>) {
        result.subscribe((res: HttpResponse<IRegla>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
