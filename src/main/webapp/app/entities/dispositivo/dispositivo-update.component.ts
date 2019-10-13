import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IDispositivo } from 'app/shared/model/dispositivo.model';
import { DispositivoService } from './dispositivo.service';

@Component({
    selector: 'jhi-dispositivo-update',
    templateUrl: './dispositivo-update.component.html'
})
export class DispositivoUpdateComponent implements OnInit {
    dispositivo: IDispositivo;
    isSaving: boolean;

    constructor(protected dispositivoService: DispositivoService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dispositivo }) => {
            this.dispositivo = dispositivo;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dispositivo.id !== undefined) {
            this.subscribeToSaveResponse(this.dispositivoService.update(this.dispositivo));
        } else {
            this.subscribeToSaveResponse(this.dispositivoService.create(this.dispositivo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDispositivo>>) {
        result.subscribe((res: HttpResponse<IDispositivo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
