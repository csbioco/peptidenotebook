import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAas } from 'app/shared/model/aas.model';
import { AccountService } from 'app/core';
import { AasService } from './aas.service';

@Component({
    selector: 'jhi-aas',
    templateUrl: './aas.component.html'
})
export class AasComponent implements OnInit, OnDestroy {
    aas: IAas[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected aasService: AasService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.aasService
            .query()
            .pipe(
                filter((res: HttpResponse<IAas[]>) => res.ok),
                map((res: HttpResponse<IAas[]>) => res.body)
            )
            .subscribe(
                (res: IAas[]) => {
                    this.aas = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAas) {
        return item.id;
    }

    registerChangeInAas() {
        this.eventSubscriber = this.eventManager.subscribe('aasListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
