import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISensor } from 'app/shared/model/sensor.model';
import { SensorService } from './sensor.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-sensor-update',
    templateUrl: './sensor-update.component.html'
})
export class SensorUpdateComponent implements OnInit {
    sensor: ISensor;
    isSaving: boolean;

    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected sensorService: SensorService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sensor }) => {
            this.sensor = sensor;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sensor.id !== undefined) {
            this.subscribeToSaveResponse(this.sensorService.update(this.sensor));
        } else {
            this.subscribeToSaveResponse(this.sensorService.create(this.sensor));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISensor>>) {
        result.subscribe((res: HttpResponse<ISensor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
