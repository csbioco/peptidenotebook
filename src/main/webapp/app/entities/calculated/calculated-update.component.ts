import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICalculated } from 'app/shared/model/calculated.model';
import { CalculatedService } from './calculated.service';
import { IUser, UserService } from 'app/core';
import { IProtocol } from 'app/shared/model/protocol.model';
import { ProtocolService } from '../protocol/protocol.service';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-calculated-update',
    templateUrl: './calculated-update.component.html'
})
export class CalculatedUpdateComponent implements OnInit {
    calculated: ICalculated;
    isSaving: boolean;
    protocols: IProtocol[];
    users: IUser[];

    constructor(
        protected protocolService: ProtocolService,
        protected jhiAlertService: JhiAlertService,
        protected calculatedService: CalculatedService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ calculated }) => {
            this.calculated = calculated;
            this.calculated.sc = 3;
            this.calculated.scale = 1;
            this.calculated.dc = 0;
            this.calculated.startresin = 1;
            this.calculated.resinunitprice = 1;
            this.calculated.substitude = 1;
            this.calculated.bound = 0;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));

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
        if (this.calculated.id !== undefined) {
            this.subscribeToSaveResponse(this.calculatedService.update(this.calculated));
        } else {
            this.subscribeToSaveResponse(this.calculatedService.create(this.calculated));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalculated>>) {
        result.subscribe((res: HttpResponse<ICalculated>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess(res: HttpResponse<ICalculated>) {
        this.isSaving = false;
        this.router.navigate(['/calculated-entry/calculated-entrycal/' + res.body.id]);
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProtocolById(index: number, item: IProtocol) {
        return item.protocolname;
    }
}
