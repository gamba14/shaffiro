import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Regla } from 'app/shared/model/regla.model';
import { ReglaService } from './regla.service';
import { ReglaComponent } from './regla.component';
import { ReglaDetailComponent } from './regla-detail.component';
import { ReglaUpdateComponent } from './regla-update.component';
import { ReglaDeletePopupComponent } from './regla-delete-dialog.component';
import { IRegla } from 'app/shared/model/regla.model';

@Injectable({ providedIn: 'root' })
export class ReglaResolve implements Resolve<IRegla> {
    constructor(private service: ReglaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRegla> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Regla>) => response.ok),
                map((regla: HttpResponse<Regla>) => regla.body)
            );
        }
        return of(new Regla());
    }
}

export const reglaRoute: Routes = [
    {
        path: '',
        component: ReglaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.regla.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ReglaDetailComponent,
        resolve: {
            regla: ReglaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.regla.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ReglaUpdateComponent,
        resolve: {
            regla: ReglaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.regla.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ReglaUpdateComponent,
        resolve: {
            regla: ReglaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.regla.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reglaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ReglaDeletePopupComponent,
        resolve: {
            regla: ReglaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shaffiroApp.regla.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
