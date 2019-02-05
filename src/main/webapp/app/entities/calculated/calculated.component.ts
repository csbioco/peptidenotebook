import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICalculated } from 'app/shared/model/calculated.model';
import { AccountService } from 'app/core';
import { CalculatedService } from './calculated.service';

@Component({
    selector: 'jhi-calculated',
    templateUrl: './calculated.component.html'
})
export class CalculatedComponent implements OnInit, OnDestroy {
    calculateds: ICalculated[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected calculatedService: CalculatedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.calculatedService
            .query()
            .pipe(
                filter((res: HttpResponse<ICalculated[]>) => res.ok),
                map((res: HttpResponse<ICalculated[]>) => res.body)
            )
            .subscribe(
                (res: ICalculated[]) => {
                    this.calculateds = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCalculateds();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICalculated) {
        return item.id;
    }

    registerChangeInCalculateds() {
        this.eventSubscriber = this.eventManager.subscribe('calculatedListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
