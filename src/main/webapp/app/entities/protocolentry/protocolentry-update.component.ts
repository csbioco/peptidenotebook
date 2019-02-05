import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProtocolentry } from 'app/shared/model/protocolentry.model';
import { ProtocolentryService } from './protocolentry.service';
import { IProtocol } from 'app/shared/model/protocol.model';
import { ProtocolService } from 'app/entities/protocol';
import { ISensor } from 'app/shared/model/sensor.model';
import { SensorService } from 'app/entities/sensor';
import { IReagent } from 'app/shared/model/reagent.model';
import { ReagentService } from 'app/entities/reagent';

@Component({
    selector: 'jhi-protocolentry-update',
    templateUrl: './protocolentry-update.component.html'
})
export class ProtocolentryUpdateComponent implements OnInit {
    protocolentry: IProtocolentry;
    isSaving: boolean;

    protocols: IProtocol[];

    sensors: ISensor[];

    reagents: IReagent[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected protocolentryService: ProtocolentryService,
        protected protocolService: ProtocolService,
        protected sensorService: SensorService,
        protected reagentService: ReagentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ protocolentry }) => {
            this.protocolentry = protocolentry;
        });
        this.protocolService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProtocol[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProtocol[]>) => response.body)
            )
            .subscribe((res: IProtocol[]) => (this.protocols = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.sensorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISensor[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISensor[]>) => response.body)
            )
            .subscribe((res: ISensor[]) => (this.sensors = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.reagentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IReagent[]>) => mayBeOk.ok),
                map((response: HttpResponse<IReagent[]>) => response.body)
            )
            .subscribe((res: IReagent[]) => (this.reagents = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.protocolentry.id !== undefined) {
            this.subscribeToSaveResponse(this.protocolentryService.update(this.protocolentry));
        } else {
            this.subscribeToSaveResponse(this.protocolentryService.create(this.protocolentry));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProtocolentry>>) {
        result.subscribe((res: HttpResponse<IProtocolentry>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProtocolById(index: number, item: IProtocol) {
        return item.id;
    }

    trackSensorById(index: number, item: ISensor) {
        return item.id;
    }

    trackReagentById(index: number, item: IReagent) {
        return item.id;
    }
}
