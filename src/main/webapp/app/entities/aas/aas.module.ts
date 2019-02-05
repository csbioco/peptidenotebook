import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PeptidenotebookSharedModule } from 'app/shared';
import {
    AasComponent,
    AasDetailComponent,
    AasUpdateComponent,
    AasDeletePopupComponent,
    AasDeleteDialogComponent,
    aasRoute,
    aasPopupRoute,
    AasAdd20PopupComponent,
    AasAdd20DialogComponent,
    AasDeleteuserPopupComponent,
    AasDeleteuserComponent,
    AasconverterComponent
} from './';

const ENTITY_STATES = [...aasRoute, ...aasPopupRoute];

@NgModule({
    imports: [PeptidenotebookSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AasComponent,
        AasDetailComponent,
        AasUpdateComponent,
        AasDeleteDialogComponent,
        AasDeletePopupComponent,
        AasAdd20PopupComponent,
        AasAdd20DialogComponent,
        AasDeleteuserPopupComponent,
        AasDeleteuserComponent,
        AasconverterComponent
    ],
    entryComponents: [
        AasComponent,
        AasUpdateComponent,
        AasDeleteDialogComponent,
        AasDeletePopupComponent,
        AasAdd20PopupComponent,
        AasAdd20DialogComponent,
        AasDeleteuserPopupComponent,
        AasDeleteuserComponent,
        AasconverterComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PeptidenotebookAasModule {}
