import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RorumSharedModule } from '../../shared';
import {
    HighCommercialRiskService,
    HighCommercialRiskPopupService,
    HighCommercialRiskComponent,
    HighCommercialRiskDetailComponent,
    HighCommercialRiskDialogComponent,
    HighCommercialRiskPopupComponent,
    HighCommercialRiskDeletePopupComponent,
    HighCommercialRiskDeleteDialogComponent,
    highCommercialRiskRoute,
    highCommercialRiskPopupRoute,
} from './';

const ENTITY_STATES = [
    ...highCommercialRiskRoute,
    ...highCommercialRiskPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HighCommercialRiskComponent,
        HighCommercialRiskDetailComponent,
        HighCommercialRiskDialogComponent,
        HighCommercialRiskDeleteDialogComponent,
        HighCommercialRiskPopupComponent,
        HighCommercialRiskDeletePopupComponent,
    ],
    entryComponents: [
        HighCommercialRiskComponent,
        HighCommercialRiskDialogComponent,
        HighCommercialRiskPopupComponent,
        HighCommercialRiskDeleteDialogComponent,
        HighCommercialRiskDeletePopupComponent,
    ],
    providers: [
        HighCommercialRiskService,
        HighCommercialRiskPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumHighCommercialRiskModule {}
