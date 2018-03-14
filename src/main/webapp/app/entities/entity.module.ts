import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {RorumGlossaryOfProcessesModule} from './glossary-of-processes/glossary-of-processes.module';
import {RorumOrganisationStructureModule} from './organisation-structure/organisation-structure.module';
import {RorumOrdersModule} from './orders/orders.module';
import {RorumGlossaryOfPurposesModule} from './glossary-of-purposes/glossary-of-purposes.module';
import {RorumSetOfSentPurposesModule} from './set-of-sent-purposes/set-of-sent-purposes.module';
import {RorumGlossaryOfMeasureUnitsModule} from './glossary-of-measure-units/glossary-of-measure-units.module';
import {RorumGlossaryOfRisksModule} from './glossary-of-risks/glossary-of-risks.module';
import {RorumGlossaryOfCommercialRisksModule} from './glossary-of-commercial-risks/glossary-of-commercial-risks.module';
import {RorumRisksPurposesModule} from './risks-purposes/risks-purposes.module';
import {RorumMeasureUnitsPurposesModule} from './measure-units-purposes/measure-units-purposes.module';
import {RorumFilledMeasureUnitsModule} from './filled-measure-units/filled-measure-units.module';
import {RorumFilledRisksModule} from './filled-risks/filled-risks.module';
import {RorumCommercialRisksPurposesModule} from './commercial-risks-purposes/commercial-risks-purposes.module';
import {RorumFilledCommercialRisksModule} from './filled-commercial-risks/filled-commercial-risks.module';

import {RorumPossibilitiesToImproveRiskModule} from './possibilities-to-improve-risk/possibilities-to-improve-risk.module';
import {RorumDecisionForRiskModule} from './decision-for-risk/decision-for-risk.module';
import {RorumHighRiskModule} from './high-risk/high-risk.module';

import {RorumHighCommercialRiskModule} from './high-commercial-risk/high-commercial-risk.module';
import {ReportingModule} from './reporting/reporting.module';
import {RorumIncidentModule} from './incident/incident.module';

/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        RorumGlossaryOfProcessesModule,
        RorumOrganisationStructureModule,
        RorumOrdersModule,
        RorumGlossaryOfPurposesModule,
        RorumSetOfSentPurposesModule,
        RorumGlossaryOfMeasureUnitsModule,
        RorumGlossaryOfRisksModule,
        RorumGlossaryOfCommercialRisksModule,
        RorumRisksPurposesModule,
        RorumMeasureUnitsPurposesModule,
        RorumFilledMeasureUnitsModule,
        RorumFilledRisksModule,
        RorumCommercialRisksPurposesModule,
        RorumFilledCommercialRisksModule,
        RorumPossibilitiesToImproveRiskModule,
        RorumDecisionForRiskModule,
        RorumHighRiskModule,
        RorumHighCommercialRiskModule,
        ReportingModule,
        RorumIncidentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumEntityModule {}
