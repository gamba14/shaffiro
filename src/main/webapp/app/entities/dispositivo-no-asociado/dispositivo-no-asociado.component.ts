import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDispositivoNoAsociado } from 'app/shared/model/dispositivo-no-asociado.model';
import { AccountService } from 'app/core';
import { DispositivoNoAsociadoService } from './dispositivo-no-asociado.service';

@Component({
    selector: 'jhi-dispositivo-no-asociado',
    templateUrl: './dispositivo-no-asociado.component.html'
})
export class DispositivoNoAsociadoComponent implements OnInit, OnDestroy {
    dispositivoNoAsociados: IDispositivoNoAsociado[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected dispositivoNoAsociadoService: DispositivoNoAsociadoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.dispositivoNoAsociadoService
            .query()
            .pipe(
                filter((res: HttpResponse<IDispositivoNoAsociado[]>) => res.ok),
                map((res: HttpResponse<IDispositivoNoAsociado[]>) => res.body)
            )
            .subscribe(
                (res: IDispositivoNoAsociado[]) => {
                    this.dispositivoNoAsociados = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDispositivoNoAsociados();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDispositivoNoAsociado) {
        return item.id;
    }

    registerChangeInDispositivoNoAsociados() {
        this.eventSubscriber = this.eventManager.subscribe('dispositivoNoAsociadoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
