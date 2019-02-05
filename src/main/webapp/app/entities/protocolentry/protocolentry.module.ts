import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PeptidenotebookSharedModule } from 'app/shared';
import {
    ProtocolentryComponent,
    ProtocolentryDetailComponent,
    ProtocolentryUpdateComponent,
    ProtocolentryDeletePopupComponent,
    ProtocolentryDeleteDialogComponent,
    protocolentryRoute,
    protocolentryPopupRoute
} from './';

const ENTITY_STATES = [...protocolentryRoute, ...protocolentryPopupRoute];

@NgModule({
    imports: [PeptidenotebookSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProtocolentryComponent,
        ProtocolentryDetailComponent,
        ProtocolentryUpdateComponent,
        ProtocolentryDeleteDialogComponent,
        ProtocolentryDeletePopupComponent
    ],
    entryComponents: [
        ProtocolentryComponent,
        ProtocolentryUpdateComponent,
        ProtocolentryDeleteDialogComponent,
        ProtocolentryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PeptidenotebookProtocolentryModule {}
