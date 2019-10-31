import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRegla } from 'app/shared/model/regla.model';
import { AccountService } from 'app/core';
import { ReglaService } from './regla.service';

@Component({
    selector: 'jhi-regla',
    templateUrl: './regla.component.html'
})
export class ReglaComponent implements OnInit, OnDestroy {
    reglas: IRegla[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected reglaService: ReglaService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.reglaService
            .query()
            .pipe(
                filter((res: HttpResponse<IRegla[]>) => res.ok),
                map((res: HttpResponse<IRegla[]>) => res.body)
            )
            .subscribe(
                (res: IRegla[]) => {
                    this.reglas = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInReglas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRegla) {
        return item.id;
    }

    registerChangeInReglas() {
        this.eventSubscriber = this.eventManager.subscribe('reglaListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
