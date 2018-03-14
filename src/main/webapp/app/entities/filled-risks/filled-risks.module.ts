import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {
    FilledRisksComponent, FilledRisksDeleteDialogComponent, FilledRisksDeletePopupComponent,
    FilledRisksDetailComponent, FilledRisksDialogComponent, FilledRisksPopupComponent, filledRisksPopupRoute,
    FilledRisksPopupService, FilledRisksResolvePagingParams, filledRisksRoute, FilledRisksService,
} from './';

const ENTITY_STATES = [
    ...filledRisksRoute,
    ...filledRisksPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FilledRisksComponent,
        FilledRisksDetailComponent,
        FilledRisksDialogComponent,
        FilledRisksDeleteDialogComponent,
        FilledRisksPopupComponent,
        FilledRisksDeletePopupComponent,
    ],
    entryComponents: [
        FilledRisksComponent,
        FilledRisksDialogComponent,
        FilledRisksPopupComponent,
        FilledRisksDeleteDialogComponent,
        FilledRisksDeletePopupComponent,
    ],
    providers: [
        FilledRisksService,
        FilledRisksPopupService,
        FilledRisksResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumFilledRisksModule {}
