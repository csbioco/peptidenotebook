import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Protocolentry } from 'app/shared/model/protocolentry.model';
import { ProtocolentryService } from './protocolentry.service';
import { ProtocolentryComponent } from './protocolentry.component';
import { ProtocolentryDetailComponent } from './protocolentry-detail.component';
import { ProtocolentryUpdateComponent } from './protocolentry-update.component';
import { ProtocolentryDeletePopupComponent } from './protocolentry-delete-dialog.component';
import { IProtocolentry } from 'app/shared/model/protocolentry.model';
import { ProtocolentrycalComponent } from './protocoentrycal/protocolentrycal.component';

@Injectable({ providedIn: 'root' })
export class ProtocolentryResolve implements Resolve<IProtocolentry> {
    constructor(private service: ProtocolentryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProtocolentry> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Protocolentry>) => response.ok),
                map((protocolentry: HttpResponse<Protocolentry>) => protocolentry.body)
            );
        }
        return of(new Protocolentry());
    }
}

export const protocolentryRoute: Routes = [
    {
        path: '',
        component: ProtocolentryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Protocolentries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProtocolentryDetailComponent,
        resolve: {
            protocolentry: ProtocolentryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Protocolentries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProtocolentryUpdateComponent,
        resolve: {
            protocolentry: ProtocolentryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Protocolentries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProtocolentryUpdateComponent,
        resolve: {
            protocolentry: ProtocolentryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Protocolentries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'protocolentriescal/:id',
        component: ProtocolentrycalComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Protocolentries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const protocolentryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProtocolentryDeletePopupComponent,
        resolve: {
            protocolentry: ProtocolentryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Protocolentries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
