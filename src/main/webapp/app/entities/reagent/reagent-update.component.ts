import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IReagent } from 'app/shared/model/reagent.model';
import { ReagentService } from './reagent.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-reagent-update',
    templateUrl: './reagent-update.component.html'
})
export class ReagentUpdateComponent implements OnInit {
    reagent: IReagent;
    isSaving: boolean;

    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected reagentService: ReagentService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reagent }) => {
            this.reagent = reagent;
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
        if (this.reagent.id !== undefined) {
            this.subscribeToSaveResponse(this.reagentService.update(this.reagent));
        } else {
            this.subscribeToSaveResponse(this.reagentService.create(this.reagent));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReagent>>) {
        result.subscribe((res: HttpResponse<IReagent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
