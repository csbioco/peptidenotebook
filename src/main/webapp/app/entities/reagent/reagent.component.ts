import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IReagent } from 'app/shared/model/reagent.model';
import { AccountService } from 'app/core';
import { ReagentService } from './reagent.service';

@Component({
    selector: 'jhi-reagent',
    templateUrl: './reagent.component.html'
})
export class ReagentComponent implements OnInit, OnDestroy {
    reagents: IReagent[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected reagentService: ReagentService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.reagentService
            .query()
            .pipe(
                filter((res: HttpResponse<IReagent[]>) => res.ok),
                map((res: HttpResponse<IReagent[]>) => res.body)
            )
            .subscribe(
                (res: IReagent[]) => {
                    this.reagents = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInReagents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IReagent) {
        return item.id;
    }

    registerChangeInReagents() {
        this.eventSubscriber = this.eventManager.subscribe('reagentListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
