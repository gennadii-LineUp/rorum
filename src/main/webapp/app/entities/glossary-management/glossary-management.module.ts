import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {glossaryManagementRoute} from "./glossary-management.route";
import {GlossaryManagementComponent} from "./glossary-management.component";
import {GlossaryManagementService} from "./glossary-management.service";
import {GlossaryManagementProcessesComponent} from "./glossaryOfProcesses/glossary-management.processes.component";
import {GlossaryManagementPurposesComponent} from "./glossaryOfPurposes/glossary-management.purposes.component";
import {GlossaryManagementMeasuresComponent} from "./glossaryOfMeasureUnits/glossary-management.measures.component";
import {GlossaryManagementCorruptionRisks} from "./glossaryOfCorruptionRisks/glossary-management.corruptionRisks";

const ENTITY_STATES = [
    ...glossaryManagementRoute
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GlossaryManagementComponent,
        GlossaryManagementProcessesComponent,
        GlossaryManagementPurposesComponent,
        GlossaryManagementMeasuresComponent,
        GlossaryManagementCorruptionRisks
    ],
    entryComponents: [
        GlossaryManagementComponent,
        GlossaryManagementProcessesComponent,
        GlossaryManagementPurposesComponent,
        GlossaryManagementMeasuresComponent,
        GlossaryManagementCorruptionRisks
    ],
    providers: [
        GlossaryManagementService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GlossaryManagementModule {}
