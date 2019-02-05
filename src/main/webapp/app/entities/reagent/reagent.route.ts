import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Reagent } from 'app/shared/model/reagent.model';
import { ReagentService } from './reagent.service';
import { ReagentComponent } from './reagent.component';
import { ReagentDetailComponent } from './reagent-detail.component';
import { ReagentUpdateComponent } from './reagent-update.component';
import { ReagentDeletePopupComponent } from './reagent-delete-dialog.component';
import { IReagent } from 'app/shared/model/reagent.model';

@Injectable({ providedIn: 'root' })
export class ReagentResolve implements Resolve<IReagent> {
    constructor(private service: ReagentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReagent> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Reagent>) => response.ok),
                map((reagent: HttpResponse<Reagent>) => reagent.body)
            );
        }
        return of(new Reagent());
    }
}

export const reagentRoute: Routes = [
    {
        path: '',
        component: ReagentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reagents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ReagentDetailComponent,
        resolve: {
            reagent: ReagentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reagents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ReagentUpdateComponent,
        resolve: {
            reagent: ReagentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reagents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ReagentUpdateComponent,
        resolve: {
            reagent: ReagentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reagents'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reagentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ReagentDeletePopupComponent,
        resolve: {
            reagent: ReagentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reagents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
