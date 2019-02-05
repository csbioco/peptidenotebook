import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProtocol } from 'app/shared/model/protocol.model';
import { ProtocolService } from './protocol.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-protocol-update',
    templateUrl: './protocol-update.component.html'
})
export class ProtocolUpdateComponent implements OnInit {
    protocol: IProtocol;
    isSaving: boolean;

    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected protocolService: ProtocolService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ protocol }) => {
            this.protocol = protocol;
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
        if (this.protocol.id !== undefined) {
            this.subscribeToSaveResponse(this.protocolService.update(this.protocol));
        } else {
            this.subscribeToSaveResponse(this.protocolService.create(this.protocol));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProtocol>>) {
        result.subscribe((res: HttpResponse<IProtocol>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
