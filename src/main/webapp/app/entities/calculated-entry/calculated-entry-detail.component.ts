import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICalculatedEntry } from 'app/shared/model/calculated-entry.model';

@Component({
    selector: 'jhi-calculated-entry-detail',
    templateUrl: './calculated-entry-detail.component.html'
})
export class CalculatedEntryDetailComponent implements OnInit {
    calculatedEntry: ICalculatedEntry;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ calculatedEntry }) => {
            this.calculatedEntry = calculatedEntry;
        });
    }

    previousState() {
        window.history.back();
    }
}
