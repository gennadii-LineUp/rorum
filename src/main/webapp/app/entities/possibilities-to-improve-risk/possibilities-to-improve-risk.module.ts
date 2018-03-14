import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {
    PossibilitiesToImproveRiskComponent, PossibilitiesToImproveRiskDeleteDialogComponent,
    PossibilitiesToImproveRiskDeletePopupComponent, PossibilitiesToImproveRiskDetailComponent,
    PossibilitiesToImproveRiskDialogComponent, PossibilitiesToImproveRiskPopupComponent,
    possibilitiesToImproveRiskPopupRoute, PossibilitiesToImproveRiskPopupService, possibilitiesToImproveRiskRoute,
    PossibilitiesToImproveRiskService,
} from './';

const ENTITY_STATES = [
    ...possibilitiesToImproveRiskRoute,
    ...possibilitiesToImproveRiskPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PossibilitiesToImproveRiskComponent,
        PossibilitiesToImproveRiskDetailComponent,
        PossibilitiesToImproveRiskDialogComponent,
        PossibilitiesToImproveRiskDeleteDialogComponent,
        PossibilitiesToImproveRiskPopupComponent,
        PossibilitiesToImproveRiskDeletePopupComponent,
    ],
    entryComponents: [
        PossibilitiesToImproveRiskComponent,
        PossibilitiesToImproveRiskDialogComponent,
        PossibilitiesToImproveRiskPopupComponent,
        PossibilitiesToImproveRiskDeleteDialogComponent,
        PossibilitiesToImproveRiskDeletePopupComponent,
    ],
    providers: [
        PossibilitiesToImproveRiskService,
        PossibilitiesToImproveRiskPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumPossibilitiesToImproveRiskModule {}
