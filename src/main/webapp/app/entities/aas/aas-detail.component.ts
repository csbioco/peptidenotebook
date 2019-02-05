import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAas } from 'app/shared/model/aas.model';

@Component({
    selector: 'jhi-aas-detail',
    templateUrl: './aas-detail.component.html'
})
export class AasDetailComponent implements OnInit {
    aas: IAas;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ aas }) => {
            this.aas = aas;
        });
    }

    previousState() {
        window.history.back();
    }
}
