import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProtocolentry } from 'app/shared/model/protocolentry.model';
import { AccountService } from 'app/core';
import { ProtocolentryService } from '../protocolentry.service';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-protocolentrycal',
    templateUrl: './protocolentrycal.component.html'
})
export class ProtocolentrycalComponent implements OnInit, OnDestroy {
    protocolentries: IProtocolentry[];
    currentAccount: any;
    eventSubscriber: Subscription;
    Entryid: any;

    constructor(
        protected protocolentryService: ProtocolentryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected router: Router
    ) {}

    loadAll() {
        this.protocolentryService
            .querycal(+this.Entryid)
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
        this.Entryid = this.router.url.split('/').pop();
        console.log(+this.Entryid);
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProtocolentries();
        this.loadAll();
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
