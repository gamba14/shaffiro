import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDispositivo } from 'app/shared/model/dispositivo.model';
import { DispositivoService } from './dispositivo.service';
import { IRegla } from 'app/shared/model/regla.model';
import { ReglaService } from 'app/entities/regla';

@Component({
    selector: 'jhi-dispositivo-update',
    templateUrl: './dispositivo-update.component.html'
})
export class DispositivoUpdateComponent implements OnInit {
    dispositivo: IDispositivo;
    isSaving: boolean;

    reglas: IRegla[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected dispositivoService: DispositivoService,
        protected reglaService: ReglaService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dispositivo }) => {
            this.dispositivo = dispositivo;
        });
        this.reglaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRegla[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRegla[]>) => response.body)
            )
            .subscribe((res: IRegla[]) => (this.reglas = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackReglaById(index: number, item: IRegla) {
        return item.id;
    }
}
