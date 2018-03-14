import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {
    HighRiskComponent, HighRiskDeleteDialogComponent, HighRiskDeletePopupComponent, HighRiskDetailComponent,
    HighRiskDialogComponent, HighRiskPopupComponent, highRiskPopupRoute, HighRiskPopupService, highRiskRoute,
    HighRiskService,
} from './';

const ENTITY_STATES = [
    ...highRiskRoute,
    ...highRiskPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HighRiskComponent,
        HighRiskDetailComponent,
        HighRiskDialogComponent,
        HighRiskDeleteDialogComponent,
        HighRiskPopupComponent,
        HighRiskDeletePopupComponent,
    ],
    entryComponents: [
        HighRiskComponent,
        HighRiskDialogComponent,
        HighRiskPopupComponent,
        HighRiskDeleteDialogComponent,
        HighRiskDeletePopupComponent,
    ],
    providers: [
        HighRiskService,
        HighRiskPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumHighRiskModule {}
