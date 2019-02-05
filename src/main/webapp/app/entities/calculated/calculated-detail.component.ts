import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICalculated } from 'app/shared/model/calculated.model';

@Component({
    selector: 'jhi-calculated-detail',
    templateUrl: './calculated-detail.component.html'
})
export class CalculatedDetailComponent implements OnInit {
    calculated: ICalculated;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ calculated }) => {
            this.calculated = calculated;
        });
    }

    previousState() {
        window.history.back();
    }
}
