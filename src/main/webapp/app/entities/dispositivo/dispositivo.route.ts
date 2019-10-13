import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Dispositivo } from 'app/shared/model/dispositivo.model';
import { DispositivoService } from './dispositivo.service';
import { DispositivoComponent } from './dispositivo.component';
import { DispositivoDetailComponent } from './dispositivo-detail.component';
import { DispositivoUpdateComponent } from './dispositivo-update.component';
import { DispositivoDeletePopupComponent } from './dispositivo-delete-dialog.component';
import { IDispositivo } from 'app/shared/model/dispositivo.model';

@Injectable({ providedIn: 'root' })
export class DispositivoResolve implements Resolve<IDispositivo> {
    constructor(private service: DispositivoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDispositivo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Dispositivo>) => response.ok),
                map((dispositivo: HttpResponse<Dispositivo>) => dispositivo.body)
            );
        }
        return of(new Dispositivo());
    }
}

export const dispositivoRoute: Routes = [
    {
        path: '',
        component: DispositivoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.dispositivo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DispositivoDetailComponent,
        resolve: {
            dispositivo: DispositivoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.dispositivo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DispositivoUpdateComponent,
        resolve: {
            dispositivo: DispositivoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.dispositivo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DispositivoUpdateComponent,
        resolve: {
            dispositivo: DispositivoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.dispositivo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dispositivoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DispositivoDeletePopupComponent,
        resolve: {
            dispositivo: DispositivoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.dispositivo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
