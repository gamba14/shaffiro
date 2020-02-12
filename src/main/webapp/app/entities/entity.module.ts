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
            },
            {
                path: 'regla',
                loadChildren: './regla/regla.module#ShaffiroReglaModule'
            },
            {
                path: 'dispositivo',
                loadChildren: './dispositivo/dispositivo.module#ShaffiroDispositivoModule'
            },
            {
                path: 'regla',
                loadChildren: './regla/regla.module#ShaffiroReglaModule'
            },
            {
                path: 'dispositivo',
                loadChildren: './dispositivo/dispositivo.module#ShaffiroDispositivoModule'
            },
            {
                path: 'regla',
                loadChildren: './regla/regla.module#ShaffiroReglaModule'
            },
            {
                path: 'regla',
                loadChildren: './regla/regla.module#ShaffiroReglaModule'
            },
            {
                path: 'dispositivo',
                loadChildren: './dispositivo/dispositivo.module#ShaffiroDispositivoModule'
            },
            {
                path: 'dispositivo',
                loadChildren: './dispositivo/dispositivo.module#ShaffiroDispositivoModule'
            },
            {
                path: 'dispositivo',
                loadChildren: './dispositivo/dispositivo.module#ShaffiroDispositivoModule'
            },
            {
                path: 'regla',
                loadChildren: './regla/regla.module#ShaffiroReglaModule'
            },
            {
                path: 'regla',
                loadChildren: './regla/regla.module#ShaffiroReglaModule'
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
