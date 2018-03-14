import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {
    FilledMeasureUnitsComponent, FilledMeasureUnitsDeleteDialogComponent, FilledMeasureUnitsDeletePopupComponent,
    FilledMeasureUnitsDetailComponent, FilledMeasureUnitsDialogComponent, FilledMeasureUnitsPopupComponent,
    filledMeasureUnitsPopupRoute, FilledMeasureUnitsPopupService, FilledMeasureUnitsResolvePagingParams,
    filledMeasureUnitsRoute, FilledMeasureUnitsService,
} from './';

const ENTITY_STATES = [
    ...filledMeasureUnitsRoute,
    ...filledMeasureUnitsPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FilledMeasureUnitsComponent,
        FilledMeasureUnitsDetailComponent,
        FilledMeasureUnitsDialogComponent,
        FilledMeasureUnitsDeleteDialogComponent,
        FilledMeasureUnitsPopupComponent,
        FilledMeasureUnitsDeletePopupComponent,
    ],
    entryComponents: [
        FilledMeasureUnitsComponent,
        FilledMeasureUnitsDialogComponent,
        FilledMeasureUnitsPopupComponent,
        FilledMeasureUnitsDeleteDialogComponent,
        FilledMeasureUnitsDeletePopupComponent,
    ],
    providers: [
        FilledMeasureUnitsService,
        FilledMeasureUnitsPopupService,
        FilledMeasureUnitsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumFilledMeasureUnitsModule {}
