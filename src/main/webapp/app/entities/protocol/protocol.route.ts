import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Protocol } from 'app/shared/model/protocol.model';
import { ProtocolService } from './protocol.service';
import { ProtocolComponent } from './protocol.component';
import { ProtocolDetailComponent } from './protocol-detail.component';
import { ProtocolUpdateComponent } from './protocol-update.component';
import { ProtocolDeletePopupComponent } from './protocol-delete-dialog.component';
import { IProtocol } from 'app/shared/model/protocol.model';

@Injectable({ providedIn: 'root' })
export class ProtocolResolve implements Resolve<IProtocol> {
    constructor(private service: ProtocolService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProtocol> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Protocol>) => response.ok),
                map((protocol: HttpResponse<Protocol>) => protocol.body)
            );
        }
        return of(new Protocol());
    }
}

export const protocolRoute: Routes = [
    {
        path: '',
        component: ProtocolComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Protocols'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProtocolDetailComponent,
        resolve: {
            protocol: ProtocolResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Protocols'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProtocolUpdateComponent,
        resolve: {
            protocol: ProtocolResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Protocols'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProtocolUpdateComponent,
        resolve: {
            protocol: ProtocolResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Protocols'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const protocolPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProtocolDeletePopupComponent,
        resolve: {
            protocol: ProtocolResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Protocols'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
