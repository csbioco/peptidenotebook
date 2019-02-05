import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'aas',
                loadChildren: './aas/aas.module#PeptidenotebookAasModule'
            },
            {
                path: 'reagent',
                loadChildren: './reagent/reagent.module#PeptidenotebookReagentModule'
            },
            {
                path: 'sensor',
                loadChildren: './sensor/sensor.module#PeptidenotebookSensorModule'
            },
            {
                path: 'protocolentry',
                loadChildren: './protocolentry/protocolentry.module#PeptidenotebookProtocolentryModule'
            },
            {
                path: 'protocol',
                loadChildren: './protocol/protocol.module#PeptidenotebookProtocolModule'
            },
            {
                path: 'calculated',
                loadChildren: './calculated/calculated.module#PeptidenotebookCalculatedModule'
            },
            {
                path: 'calculated-entry',
                loadChildren: './calculated-entry/calculated-entry.module#PeptidenotebookCalculatedEntryModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PeptidenotebookEntityModule {}
