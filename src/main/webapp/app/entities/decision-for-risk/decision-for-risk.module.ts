import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {
    DecisionForRiskComponent, DecisionForRiskDeleteDialogComponent, DecisionForRiskDeletePopupComponent,
    DecisionForRiskDetailComponent, DecisionForRiskDialogComponent, DecisionForRiskPopupComponent,
    decisionForRiskPopupRoute, DecisionForRiskPopupService, decisionForRiskRoute, DecisionForRiskService,
} from './';

const ENTITY_STATES = [
    ...decisionForRiskRoute,
    ...decisionForRiskPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DecisionForRiskComponent,
        DecisionForRiskDetailComponent,
        DecisionForRiskDialogComponent,
        DecisionForRiskDeleteDialogComponent,
        DecisionForRiskPopupComponent,
        DecisionForRiskDeletePopupComponent,
    ],
    entryComponents: [
        DecisionForRiskComponent,
        DecisionForRiskDialogComponent,
        DecisionForRiskPopupComponent,
        DecisionForRiskDeleteDialogComponent,
        DecisionForRiskDeletePopupComponent,
    ],
    providers: [
        DecisionForRiskService,
        DecisionForRiskPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumDecisionForRiskModule {}
