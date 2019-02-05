import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PeptidenotebookSharedModule } from 'app/shared';
import {
    ReagentComponent,
    ReagentDetailComponent,
    ReagentUpdateComponent,
    ReagentDeletePopupComponent,
    ReagentDeleteDialogComponent,
    reagentRoute,
    reagentPopupRoute
} from './';

const ENTITY_STATES = [...reagentRoute, ...reagentPopupRoute];

@NgModule({
    imports: [PeptidenotebookSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReagentComponent,
        ReagentDetailComponent,
        ReagentUpdateComponent,
        ReagentDeleteDialogComponent,
        ReagentDeletePopupComponent
    ],
    entryComponents: [ReagentComponent, ReagentUpdateComponent, ReagentDeleteDialogComponent, ReagentDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PeptidenotebookReagentModule {}
