import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ShaffiroSharedModule } from 'app/shared';
import {
    DispositivoComponent,
    DispositivoDetailComponent,
    DispositivoUpdateComponent,
    DispositivoDeletePopupComponent,
    DispositivoDeleteDialogComponent,
    dispositivoRoute,
    dispositivoPopupRoute
} from './';

const ENTITY_STATES = [...dispositivoRoute, ...dispositivoPopupRoute];

@NgModule({
    imports: [ShaffiroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DispositivoComponent,
        DispositivoDetailComponent,
        DispositivoUpdateComponent,
        DispositivoDeleteDialogComponent,
        DispositivoDeletePopupComponent
    ],
    entryComponents: [DispositivoComponent, DispositivoUpdateComponent, DispositivoDeleteDialogComponent, DispositivoDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShaffiroDispositivoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
