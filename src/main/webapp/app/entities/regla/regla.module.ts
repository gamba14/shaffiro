import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ShaffiroSharedModule } from 'app/shared';
import {
    ReglaComponent,
    ReglaDetailComponent,
    ReglaUpdateComponent,
    ReglaDeletePopupComponent,
    ReglaDeleteDialogComponent,
    reglaRoute,
    reglaPopupRoute
} from './';

const ENTITY_STATES = [...reglaRoute, ...reglaPopupRoute];

@NgModule({
    imports: [ShaffiroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ReglaComponent, ReglaDetailComponent, ReglaUpdateComponent, ReglaDeleteDialogComponent, ReglaDeletePopupComponent],
    entryComponents: [ReglaComponent, ReglaUpdateComponent, ReglaDeleteDialogComponent, ReglaDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShaffiroReglaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
