import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PeptidenotebookSharedModule } from 'app/shared';
import {
    CalculatedComponent,
    CalculatedDetailComponent,
    CalculatedUpdateComponent,
    CalculatedDeletePopupComponent,
    CalculatedDeleteDialogComponent,
    calculatedRoute,
    calculatedPopupRoute
} from './';

const ENTITY_STATES = [...calculatedRoute, ...calculatedPopupRoute];

@NgModule({
    imports: [PeptidenotebookSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CalculatedComponent,
        CalculatedDetailComponent,
        CalculatedUpdateComponent,
        CalculatedDeleteDialogComponent,
        CalculatedDeletePopupComponent
    ],
    entryComponents: [CalculatedComponent, CalculatedUpdateComponent, CalculatedDeleteDialogComponent, CalculatedDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PeptidenotebookCalculatedModule {}
