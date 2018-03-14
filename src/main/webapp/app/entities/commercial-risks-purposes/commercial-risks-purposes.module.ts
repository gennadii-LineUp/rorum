import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {
    CommercialRisksPurposesComponent, CommercialRisksPurposesDeleteDialogComponent,
    CommercialRisksPurposesDeletePopupComponent, CommercialRisksPurposesDetailComponent,
    CommercialRisksPurposesDialogComponent, CommercialRisksPurposesPopupComponent, commercialRisksPurposesPopupRoute,
    CommercialRisksPurposesPopupService, CommercialRisksPurposesResolvePagingParams, commercialRisksPurposesRoute,
    CommercialRisksPurposesService,
} from './';

const ENTITY_STATES = [
    ...commercialRisksPurposesRoute,
    ...commercialRisksPurposesPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CommercialRisksPurposesComponent,
        CommercialRisksPurposesDetailComponent,
        CommercialRisksPurposesDialogComponent,
        CommercialRisksPurposesDeleteDialogComponent,
        CommercialRisksPurposesPopupComponent,
        CommercialRisksPurposesDeletePopupComponent,
    ],
    entryComponents: [
        CommercialRisksPurposesComponent,
        CommercialRisksPurposesDialogComponent,
        CommercialRisksPurposesPopupComponent,
        CommercialRisksPurposesDeleteDialogComponent,
        CommercialRisksPurposesDeletePopupComponent,
    ],
    providers: [
        CommercialRisksPurposesService,
        CommercialRisksPurposesPopupService,
        CommercialRisksPurposesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumCommercialRisksPurposesModule {}
