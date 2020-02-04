import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRegla } from 'app/shared/model/regla.model';
import { ReglaService } from './regla.service';
import { IDispositivo } from 'app/shared/model/dispositivo.model';
import { DispositivoService } from 'app/entities/dispositivo';

@Component({
    selector: 'jhi-regla-update',
    templateUrl: './regla-update.component.html'
})
export class ReglaUpdateComponent implements OnInit {
    regla: IRegla;
    isSaving: boolean;

    dispositivos: IDispositivo[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected reglaService: ReglaService,
        protected dispositivoService: DispositivoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ regla }) => {
            this.regla = regla;
        });
        this.dispositivoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDispositivo[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDispositivo[]>) => response.body)
            )
            .subscribe((res: IDispositivo[]) => (this.dispositivos = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDispositivoById(index: number, item: IDispositivo) {
        return item.id;
    }
}
