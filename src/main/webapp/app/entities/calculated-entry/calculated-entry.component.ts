import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICalculatedEntry } from 'app/shared/model/calculated-entry.model';
import { AccountService } from 'app/core';
import { CalculatedEntryService } from './calculated-entry.service';

@Component({
    selector: 'jhi-calculated-entry',
    templateUrl: './calculated-entry.component.html'
})
export class CalculatedEntryComponent implements OnInit, OnDestroy {
    calculatedEntries: ICalculatedEntry[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected calculatedEntryService: CalculatedEntryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.calculatedEntryService
            .query()
            .pipe(
                filter((res: HttpResponse<ICalculatedEntry[]>) => res.ok),
                map((res: HttpResponse<ICalculatedEntry[]>) => res.body)
            )
            .subscribe(
                (res: ICalculatedEntry[]) => {
                    this.calculatedEntries = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCalculatedEntries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICalculatedEntry) {
        return item.id;
    }

    registerChangeInCalculatedEntries() {
        this.eventSubscriber = this.eventManager.subscribe('calculatedEntryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
