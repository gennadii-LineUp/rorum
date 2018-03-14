import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {
    RisksPurposesComponent, RisksPurposesDeleteDialogComponent, RisksPurposesDeletePopupComponent,
    RisksPurposesDetailComponent, RisksPurposesDialogComponent, RisksPurposesPopupComponent, risksPurposesPopupRoute,
    RisksPurposesPopupService, RisksPurposesResolvePagingParams, risksPurposesRoute, RisksPurposesService,
} from './';

const ENTITY_STATES = [
    ...risksPurposesRoute,
    ...risksPurposesPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RisksPurposesComponent,
        RisksPurposesDetailComponent,
        RisksPurposesDialogComponent,
        RisksPurposesDeleteDialogComponent,
        RisksPurposesPopupComponent,
        RisksPurposesDeletePopupComponent,
    ],
    entryComponents: [
        RisksPurposesComponent,
        RisksPurposesDialogComponent,
        RisksPurposesPopupComponent,
        RisksPurposesDeleteDialogComponent,
        RisksPurposesDeletePopupComponent,
    ],
    providers: [
        RisksPurposesService,
        RisksPurposesPopupService,
        RisksPurposesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumRisksPurposesModule {}
