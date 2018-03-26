import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {glossaryManagementRoute} from "./glossary-management.route";
import {GlossaryManagementComponent} from "./glossary-management.component";
import {GlossaryManagementService} from "./glossary-management.service";
import {GlossaryManagementProcessesComponent} from "./glossaryOfProcesses/glossary-management.processes.component";
import {GlossaryManagementPurposesComponent} from "./glossaryOfPurposes/glossary-management.purposes.component";
import {GlossaryManagementMeasuresComponent} from "./glossaryOfMeasureUnits/glossary-management.measures.component";
import {ReactiveFormsModule} from "@angular/forms";
import {GlossaryManagementControlMechanismsComponent} from "./glossaryOfControlMechanisms/glossary-management.control-mechanisms.component";
import {GlossaryManagementCorruptionRisksComponent} from "./glossaryOfCorruptionRisks/glossary-management.corruptionRisks.component";
import {GlossaryManagementKRIComponent} from "./glossaryOfKRI/glossary-management.KRI.component";
import {GlossaryManagementRisksComponent} from "./glossaryOfRisks/glossary-management.risks.component";

const ENTITY_STATES = [
    ...glossaryManagementRoute
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        ReactiveFormsModule
    ],
    declarations: [
        GlossaryManagementComponent,
        GlossaryManagementProcessesComponent,
        GlossaryManagementPurposesComponent,
        GlossaryManagementMeasuresComponent,
        GlossaryManagementCorruptionRisksComponent,
        GlossaryManagementControlMechanismsComponent,
        GlossaryManagementKRIComponent,
        GlossaryManagementRisksComponent
    ],
    entryComponents: [
        GlossaryManagementComponent,
        GlossaryManagementProcessesComponent,
        GlossaryManagementPurposesComponent,
        GlossaryManagementMeasuresComponent,
        GlossaryManagementCorruptionRisksComponent,
        GlossaryManagementControlMechanismsComponent,
        GlossaryManagementKRIComponent,
        GlossaryManagementRisksComponent
    ],
    providers: [
        GlossaryManagementService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GlossaryManagementModule {}
