import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DispositivoNoAsociado } from 'app/shared/model/dispositivo-no-asociado.model';
import { DispositivoNoAsociadoService } from './dispositivo-no-asociado.service';
import { DispositivoNoAsociadoComponent } from './dispositivo-no-asociado.component';
import { DispositivoNoAsociadoDetailComponent } from './dispositivo-no-asociado-detail.component';
import { DispositivoNoAsociadoUpdateComponent } from './dispositivo-no-asociado-update.component';
import { DispositivoNoAsociadoDeletePopupComponent } from './dispositivo-no-asociado-delete-dialog.component';
import { IDispositivoNoAsociado } from 'app/shared/model/dispositivo-no-asociado.model';

@Injectable({ providedIn: 'root' })
export class DispositivoNoAsociadoResolve implements Resolve<IDispositivoNoAsociado> {
    constructor(private service: DispositivoNoAsociadoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDispositivoNoAsociado> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DispositivoNoAsociado>) => response.ok),
                map((dispositivoNoAsociado: HttpResponse<DispositivoNoAsociado>) => dispositivoNoAsociado.body)
            );
        }
        return of(new DispositivoNoAsociado());
    }
}

export const dispositivoNoAsociadoRoute: Routes = [
    {
        path: '',
        component: DispositivoNoAsociadoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.dispositivoNoAsociado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DispositivoNoAsociadoDetailComponent,
        resolve: {
            dispositivoNoAsociado: DispositivoNoAsociadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.dispositivoNoAsociado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DispositivoNoAsociadoUpdateComponent,
        resolve: {
            dispositivoNoAsociado: DispositivoNoAsociadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.dispositivoNoAsociado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DispositivoNoAsociadoUpdateComponent,
        resolve: {
            dispositivoNoAsociado: DispositivoNoAsociadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.dispositivoNoAsociado.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dispositivoNoAsociadoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DispositivoNoAsociadoDeletePopupComponent,
        resolve: {
            dispositivoNoAsociado: DispositivoNoAsociadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.dispositivoNoAsociado.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
