import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProtocol } from 'app/shared/model/protocol.model';
import { AccountService } from 'app/core';
import { ProtocolService } from './protocol.service';

@Component({
    selector: 'jhi-protocol',
    templateUrl: './protocol.component.html'
})
export class ProtocolComponent implements OnInit, OnDestroy {
    protocols: IProtocol[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected protocolService: ProtocolService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.protocolService
            .query()
            .pipe(
                filter((res: HttpResponse<IProtocol[]>) => res.ok),
                map((res: HttpResponse<IProtocol[]>) => res.body)
            )
            .subscribe(
                (res: IProtocol[]) => {
                    this.protocols = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProtocols();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProtocol) {
        return item.id;
    }

    registerChangeInProtocols() {
        this.eventSubscriber = this.eventManager.subscribe('protocolListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
