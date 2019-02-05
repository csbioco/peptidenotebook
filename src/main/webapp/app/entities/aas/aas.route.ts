import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Aas } from 'app/shared/model/aas.model';
import { AasService } from './aas.service';
import { AasComponent } from './aas.component';
import { AasDetailComponent } from './aas-detail.component';
import { AasUpdateComponent } from './aas-update.component';
import { AasDeletePopupComponent } from './aas-delete-dialog.component';
import { IAas } from 'app/shared/model/aas.model';
import { AasAdd20PopupComponent } from './add_20/aas-add20.component';
import { AasDeleteuserPopupComponent } from './add_20/aas-deleteuser.component';
import { AasconverterComponent } from './converter/aasconverter.component';
@Injectable({ providedIn: 'root' })
export class AasResolve implements Resolve<IAas> {
    constructor(private service: AasService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAas> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Aas>) => response.ok),
                map((aas: HttpResponse<Aas>) => aas.body)
            );
        }
        return of(new Aas());
    }
}

export const aasRoute: Routes = [
    {
        path: '',
        component: AasComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Aas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AasDetailComponent,
        resolve: {
            aas: AasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Aas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AasUpdateComponent,
        resolve: {
            aas: AasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Aas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AasUpdateComponent,
        resolve: {
            aas: AasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Aas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'converter',
        component: AasconverterComponent,
        resolve: {
            aas: AasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Aas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const aasPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AasDeletePopupComponent,
        resolve: {
            aas: AasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Aas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aasall',
        component: AasDeleteuserPopupComponent,
        resolve: {
            aas: AasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Aas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'addaas',
        component: AasAdd20PopupComponent,
        resolve: {
            aas: AasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Aas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
