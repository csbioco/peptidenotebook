import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { PeptidenotebookSharedLibsModule, PeptidenotebookSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [PeptidenotebookSharedLibsModule, PeptidenotebookSharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    exports: [PeptidenotebookSharedCommonModule, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PeptidenotebookSharedModule {
    static forRoot() {
        return {
            ngModule: PeptidenotebookSharedModule
        };
    }
}
