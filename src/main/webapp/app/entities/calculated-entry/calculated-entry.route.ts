import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CalculatedEntry } from 'app/shared/model/calculated-entry.model';
import { CalculatedEntryService } from './calculated-entry.service';
import { CalculatedEntryComponent } from './calculated-entry.component';
import { CalculatedEntryDetailComponent } from './calculated-entry-detail.component';
import { CalculatedEntryUpdateComponent } from './calculated-entry-update.component';
import { CalculatedEntryDeletePopupComponent } from './calculated-entry-delete-dialog.component';
import { ICalculatedEntry } from 'app/shared/model/calculated-entry.model';
import { CalculatedEntryFindbyidComponent } from './calculated-entry-findbyid/calculated-entry-findbyid.component';

@Injectable({ providedIn: 'root' })
export class CalculatedEntryResolve implements Resolve<ICalculatedEntry> {
    constructor(private service: CalculatedEntryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICalculatedEntry> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CalculatedEntry>) => response.ok),
                map((calculatedEntry: HttpResponse<CalculatedEntry>) => calculatedEntry.body)
            );
        }
        return of(new CalculatedEntry());
    }
}

export const calculatedEntryRoute: Routes = [
    {
        path: '',
        component: CalculatedEntryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CalculatedEntries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CalculatedEntryDetailComponent,
        resolve: {
            calculatedEntry: CalculatedEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CalculatedEntries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CalculatedEntryUpdateComponent,
        resolve: {
            calculatedEntry: CalculatedEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CalculatedEntries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CalculatedEntryUpdateComponent,
        resolve: {
            calculatedEntry: CalculatedEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CalculatedEntries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'calculated-entrycal/:id',
        component: CalculatedEntryFindbyidComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CalculatedEntriesbyid'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const calculatedEntryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CalculatedEntryDeletePopupComponent,
        resolve: {
            calculatedEntry: CalculatedEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CalculatedEntries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
