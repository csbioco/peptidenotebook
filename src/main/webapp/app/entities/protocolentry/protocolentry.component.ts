import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProtocolentry } from 'app/shared/model/protocolentry.model';
import { AccountService } from 'app/core';
import { ProtocolentryService } from './protocolentry.service';

@Component({
    selector: 'jhi-protocolentry',
    templateUrl: './protocolentry.component.html'
})
export class ProtocolentryComponent implements OnInit, OnDestroy {
    protocolentries: IProtocolentry[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected protocolentryService: ProtocolentryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.protocolentryService
            .query()
            .pipe(
                filter((res: HttpResponse<IProtocolentry[]>) => res.ok),
                map((res: HttpResponse<IProtocolentry[]>) => res.body)
            )
            .subscribe(
                (res: IProtocolentry[]) => {
                    this.protocolentries = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProtocolentries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProtocolentry) {
        return item.id;
    }

    registerChangeInProtocolentries() {
        this.eventSubscriber = this.eventManager.subscribe('protocolentryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
