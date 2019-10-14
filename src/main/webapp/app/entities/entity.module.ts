import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'dispositivo',
                loadChildren: './dispositivo/dispositivo.module#ShaffiroDispositivoModule'
            },
            {
                path: 'dispositivo-no-asociado',
                loadChildren: './dispositivo-no-asociado/dispositivo-no-asociado.module#ShaffiroDispositivoNoAsociadoModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShaffiroEntityModule {}
