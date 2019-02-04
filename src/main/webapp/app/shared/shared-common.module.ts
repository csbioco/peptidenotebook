import { NgModule } from '@angular/core';

import { PeptidenotebookSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [PeptidenotebookSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [PeptidenotebookSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PeptidenotebookSharedCommonModule {}
