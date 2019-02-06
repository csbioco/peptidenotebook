import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PeptidenotebookSharedModule } from 'app/shared';
import {
    CalculatedEntryComponent,
    CalculatedEntryDetailComponent,
    CalculatedEntryUpdateComponent,
    CalculatedEntryDeletePopupComponent,
    CalculatedEntryDeleteDialogComponent,
    calculatedEntryRoute,
    calculatedEntryPopupRoute,
    CalculatedEntryFindbyidComponent
} from './';

const ENTITY_STATES = [...calculatedEntryRoute, ...calculatedEntryPopupRoute];

@NgModule({
    imports: [PeptidenotebookSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CalculatedEntryComponent,
        CalculatedEntryDetailComponent,
        CalculatedEntryUpdateComponent,
        CalculatedEntryDeleteDialogComponent,
        CalculatedEntryDeletePopupComponent,
        CalculatedEntryFindbyidComponent
    ],
    entryComponents: [
        CalculatedEntryComponent,
        CalculatedEntryUpdateComponent,
        CalculatedEntryDeleteDialogComponent,
        CalculatedEntryDeletePopupComponent,
        CalculatedEntryFindbyidComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PeptidenotebookCalculatedEntryModule {}
