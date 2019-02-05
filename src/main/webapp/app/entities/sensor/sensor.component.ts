import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISensor } from 'app/shared/model/sensor.model';
import { AccountService } from 'app/core';
import { SensorService } from './sensor.service';

@Component({
    selector: 'jhi-sensor',
    templateUrl: './sensor.component.html'
})
export class SensorComponent implements OnInit, OnDestroy {
    sensors: ISensor[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected sensorService: SensorService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.sensorService
            .query()
            .pipe(
                filter((res: HttpResponse<ISensor[]>) => res.ok),
                map((res: HttpResponse<ISensor[]>) => res.body)
            )
            .subscribe(
                (res: ISensor[]) => {
                    this.sensors = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSensors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISensor) {
        return item.id;
    }

    registerChangeInSensors() {
        this.eventSubscriber = this.eventManager.subscribe('sensorListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
