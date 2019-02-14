import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICalculatedEntry } from 'app/shared/model/calculated-entry.model';
import { CalculatedEntryService } from './calculated-entry.service';
import { ICalculated } from 'app/shared/model/calculated.model';
import { CalculatedService } from 'app/entities/calculated';

import { IProtocol } from 'app/shared/model/protocol.model';
import { ProtocolService } from '../protocol/protocol.service';
@Component({
    selector: 'jhi-calculated-entry-update',
    templateUrl: './calculated-entry-update.component.html'
})
export class CalculatedEntryUpdateComponent implements OnInit {
    calculatedEntry: ICalculatedEntry;
    isSaving: boolean;
    protocols: IProtocol[];

    calculateds: ICalculated[];

    constructor(
        protected protocolService: ProtocolService,
        protected jhiAlertService: JhiAlertService,
        protected calculatedEntryService: CalculatedEntryService,
        protected calculatedService: CalculatedService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ calculatedEntry }) => {
            this.calculatedEntry = calculatedEntry;
        });
        this.calculatedService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICalculated[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICalculated[]>) => response.body)
            )
            .subscribe((res: ICalculated[]) => (this.calculateds = res), (res: HttpErrorResponse) => this.onError(res.message));

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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.calculatedEntry.id !== undefined) {
            this.subscribeToSaveResponse(this.calculatedEntryService.update(this.calculatedEntry));
        } else {
            this.subscribeToSaveResponse(this.calculatedEntryService.create(this.calculatedEntry));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalculatedEntry>>) {
        result.subscribe((res: HttpResponse<ICalculatedEntry>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCalculatedById(index: number, item: ICalculated) {
        return item.id;
    }
    trackProtocolById(index: number, item: IProtocol) {
        return item.protocolname;
    }
}
