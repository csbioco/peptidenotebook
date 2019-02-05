import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IAas } from 'app/shared/model/aas.model';
import { AasService } from './aas.service';

@Component({
    selector: 'jhi-aas-update',
    templateUrl: './aas-update.component.html'
})
export class AasUpdateComponent implements OnInit {
    aas: IAas;
    isSaving: boolean;

    constructor(protected aasService: AasService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ aas }) => {
            this.aas = aas;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.aas.id !== undefined) {
            this.subscribeToSaveResponse(this.aasService.update(this.aas));
        } else {
            this.subscribeToSaveResponse(this.aasService.create(this.aas));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAas>>) {
        result.subscribe((res: HttpResponse<IAas>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
