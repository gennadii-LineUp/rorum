import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {
    MeasureUnitsPurposesComponent, MeasureUnitsPurposesDeleteDialogComponent,
    MeasureUnitsPurposesDeletePopupComponent, MeasureUnitsPurposesDetailComponent, MeasureUnitsPurposesDialogComponent,
    MeasureUnitsPurposesPopupComponent, measureUnitsPurposesPopupRoute, MeasureUnitsPurposesPopupService,
    MeasureUnitsPurposesResolvePagingParams, measureUnitsPurposesRoute, MeasureUnitsPurposesService,
} from './';

const ENTITY_STATES = [
    ...measureUnitsPurposesRoute,
    ...measureUnitsPurposesPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MeasureUnitsPurposesComponent,
        MeasureUnitsPurposesDetailComponent,
        MeasureUnitsPurposesDialogComponent,
        MeasureUnitsPurposesDeleteDialogComponent,
        MeasureUnitsPurposesPopupComponent,
        MeasureUnitsPurposesDeletePopupComponent,
    ],
    entryComponents: [
        MeasureUnitsPurposesComponent,
        MeasureUnitsPurposesDialogComponent,
        MeasureUnitsPurposesPopupComponent,
        MeasureUnitsPurposesDeleteDialogComponent,
        MeasureUnitsPurposesDeletePopupComponent,
    ],
    providers: [
        MeasureUnitsPurposesService,
        MeasureUnitsPurposesPopupService,
        MeasureUnitsPurposesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumMeasureUnitsPurposesModule {}
