import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProtocolentry } from 'app/shared/model/protocolentry.model';

@Component({
    selector: 'jhi-protocolentry-detail',
    templateUrl: './protocolentry-detail.component.html'
})
export class ProtocolentryDetailComponent implements OnInit {
    protocolentry: IProtocolentry;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ protocolentry }) => {
            this.protocolentry = protocolentry;
        });
    }

    previousState() {
        window.history.back();
    }
}
