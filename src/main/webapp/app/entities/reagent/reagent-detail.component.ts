import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReagent } from 'app/shared/model/reagent.model';

@Component({
    selector: 'jhi-reagent-detail',
    templateUrl: './reagent-detail.component.html'
})
export class ReagentDetailComponent implements OnInit {
    reagent: IReagent;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reagent }) => {
            this.reagent = reagent;
        });
    }

    previousState() {
        window.history.back();
    }
}
