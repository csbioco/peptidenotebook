import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Calculated } from 'app/shared/model/calculated.model';
import { CalculatedService } from './calculated.service';
import { CalculatedComponent } from './calculated.component';
import { CalculatedDetailComponent } from './calculated-detail.component';
import { CalculatedUpdateComponent } from './calculated-update.component';
import { CalculatedDeletePopupComponent } from './calculated-delete-dialog.component';
import { ICalculated } from 'app/shared/model/calculated.model';

@Injectable({ providedIn: 'root' })
export class CalculatedResolve implements Resolve<ICalculated> {
    constructor(private service: CalculatedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICalculated> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Calculated>) => response.ok),
                map((calculated: HttpResponse<Calculated>) => calculated.body)
            );
        }
        return of(new Calculated());
    }
}

export const calculatedRoute: Routes = [
    {
        path: '',
        component: CalculatedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Calculateds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CalculatedDetailComponent,
        resolve: {
            calculated: CalculatedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Calculateds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CalculatedUpdateComponent,
        resolve: {
            calculated: CalculatedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Calculateds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CalculatedUpdateComponent,
        resolve: {
            calculated: CalculatedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Calculateds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const calculatedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CalculatedDeletePopupComponent,
        resolve: {
            calculated: CalculatedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Calculateds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
