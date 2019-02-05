import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PeptidenotebookSharedModule } from 'app/shared';
import {
    ProtocolComponent,
    ProtocolDetailComponent,
    ProtocolUpdateComponent,
    ProtocolDeletePopupComponent,
    ProtocolDeleteDialogComponent,
    protocolRoute,
    protocolPopupRoute
} from './';

const ENTITY_STATES = [...protocolRoute, ...protocolPopupRoute];

@NgModule({
    imports: [PeptidenotebookSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProtocolComponent,
        ProtocolDetailComponent,
        ProtocolUpdateComponent,
        ProtocolDeleteDialogComponent,
        ProtocolDeletePopupComponent
    ],
    entryComponents: [ProtocolComponent, ProtocolUpdateComponent, ProtocolDeleteDialogComponent, ProtocolDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PeptidenotebookProtocolModule {}
