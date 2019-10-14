import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ShaffiroSharedModule } from 'app/shared';
import {
    DispositivoNoAsociadoComponent,
    DispositivoNoAsociadoDetailComponent,
    DispositivoNoAsociadoUpdateComponent,
    DispositivoNoAsociadoDeletePopupComponent,
    DispositivoNoAsociadoDeleteDialogComponent,
    dispositivoNoAsociadoRoute,
    dispositivoNoAsociadoPopupRoute
} from './';

const ENTITY_STATES = [...dispositivoNoAsociadoRoute, ...dispositivoNoAsociadoPopupRoute];

@NgModule({
    imports: [ShaffiroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DispositivoNoAsociadoComponent,
        DispositivoNoAsociadoDetailComponent,
        DispositivoNoAsociadoUpdateComponent,
        DispositivoNoAsociadoDeleteDialogComponent,
        DispositivoNoAsociadoDeletePopupComponent
    ],
    entryComponents: [
        DispositivoNoAsociadoComponent,
        DispositivoNoAsociadoUpdateComponent,
        DispositivoNoAsociadoDeleteDialogComponent,
        DispositivoNoAsociadoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShaffiroDispositivoNoAsociadoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
