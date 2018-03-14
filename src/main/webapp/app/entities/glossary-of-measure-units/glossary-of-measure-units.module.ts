import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {
    GlossaryOfMeasureUnitsComponent, GlossaryOfMeasureUnitsDeleteDialogComponent,
    GlossaryOfMeasureUnitsDeletePopupComponent, GlossaryOfMeasureUnitsDetailComponent,
    GlossaryOfMeasureUnitsDialogComponent, GlossaryOfMeasureUnitsPopupComponent, glossaryOfMeasureUnitsPopupRoute,
    GlossaryOfMeasureUnitsPopupService, GlossaryOfMeasureUnitsResolvePagingParams, glossaryOfMeasureUnitsRoute,
    GlossaryOfMeasureUnitsService,
} from './';

const ENTITY_STATES = [
    ...glossaryOfMeasureUnitsRoute,
    ...glossaryOfMeasureUnitsPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GlossaryOfMeasureUnitsComponent,
        GlossaryOfMeasureUnitsDetailComponent,
        GlossaryOfMeasureUnitsDialogComponent,
        GlossaryOfMeasureUnitsDeleteDialogComponent,
        GlossaryOfMeasureUnitsPopupComponent,
        GlossaryOfMeasureUnitsDeletePopupComponent,
    ],
    entryComponents: [
        GlossaryOfMeasureUnitsComponent,
        GlossaryOfMeasureUnitsDialogComponent,
        GlossaryOfMeasureUnitsPopupComponent,
        GlossaryOfMeasureUnitsDeleteDialogComponent,
        GlossaryOfMeasureUnitsDeletePopupComponent,
    ],
    providers: [
        GlossaryOfMeasureUnitsService,
        GlossaryOfMeasureUnitsPopupService,
        GlossaryOfMeasureUnitsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumGlossaryOfMeasureUnitsModule {}
