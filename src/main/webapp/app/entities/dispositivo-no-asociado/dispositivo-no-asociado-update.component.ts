import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IDispositivoNoAsociado } from 'app/shared/model/dispositivo-no-asociado.model';
import { DispositivoNoAsociadoService } from './dispositivo-no-asociado.service';

@Component({
    selector: 'jhi-dispositivo-no-asociado-update',
    templateUrl: './dispositivo-no-asociado-update.component.html'
})
export class DispositivoNoAsociadoUpdateComponent implements OnInit {
    dispositivoNoAsociado: IDispositivoNoAsociado;
    isSaving: boolean;

    constructor(protected dispositivoNoAsociadoService: DispositivoNoAsociadoService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dispositivoNoAsociado }) => {
            this.dispositivoNoAsociado = dispositivoNoAsociado;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dispositivoNoAsociado.id !== undefined) {
            this.subscribeToSaveResponse(this.dispositivoNoAsociadoService.update(this.dispositivoNoAsociado));
        } else {
            this.subscribeToSaveResponse(this.dispositivoNoAsociadoService.create(this.dispositivoNoAsociado));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDispositivoNoAsociado>>) {
        result.subscribe(
            (res: HttpResponse<IDispositivoNoAsociado>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
