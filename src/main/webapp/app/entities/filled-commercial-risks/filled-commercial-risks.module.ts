import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {
    FilledCommercialRisksComponent, FilledCommercialRisksDeleteDialogComponent,
    FilledCommercialRisksDeletePopupComponent, FilledCommercialRisksDetailComponent,
    FilledCommercialRisksDialogComponent, FilledCommercialRisksPopupComponent, filledCommercialRisksPopupRoute,
    FilledCommercialRisksPopupService, FilledCommercialRisksResolvePagingParams, filledCommercialRisksRoute,
    FilledCommercialRisksService,
} from './';

const ENTITY_STATES = [
    ...filledCommercialRisksRoute,
    ...filledCommercialRisksPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FilledCommercialRisksComponent,
        FilledCommercialRisksDetailComponent,
        FilledCommercialRisksDialogComponent,
        FilledCommercialRisksDeleteDialogComponent,
        FilledCommercialRisksPopupComponent,
        FilledCommercialRisksDeletePopupComponent,
    ],
    entryComponents: [
        FilledCommercialRisksComponent,
        FilledCommercialRisksDialogComponent,
        FilledCommercialRisksPopupComponent,
        FilledCommercialRisksDeleteDialogComponent,
        FilledCommercialRisksDeletePopupComponent,
    ],
    providers: [
        FilledCommercialRisksService,
        FilledCommercialRisksPopupService,
        FilledCommercialRisksResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumFilledCommercialRisksModule {}
