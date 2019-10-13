import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDispositivo } from 'app/shared/model/dispositivo.model';
import { AccountService } from 'app/core';
import { DispositivoService } from './dispositivo.service';

@Component({
    selector: 'jhi-dispositivo',
    templateUrl: './dispositivo.component.html'
})
export class DispositivoComponent implements OnInit, OnDestroy {
    dispositivos: IDispositivo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected dispositivoService: DispositivoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.dispositivoService
            .query()
            .pipe(
                filter((res: HttpResponse<IDispositivo[]>) => res.ok),
                map((res: HttpResponse<IDispositivo[]>) => res.body)
            )
            .subscribe(
                (res: IDispositivo[]) => {
                    this.dispositivos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDispositivos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDispositivo) {
        return item.id;
    }

    registerChangeInDispositivos() {
        this.eventSubscriber = this.eventManager.subscribe('dispositivoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
