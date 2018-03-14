import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {RorumAdminModule} from '../../admin/admin.module';
import {
    GlossaryOfCommercialRisksComponent, GlossaryOfCommercialRisksDeleteDialogComponent,
    GlossaryOfCommercialRisksDeletePopupComponent, GlossaryOfCommercialRisksDetailComponent,
    GlossaryOfCommercialRisksDialogComponent, GlossaryOfCommercialRisksPopupComponent,
    glossaryOfCommercialRisksPopupRoute, GlossaryOfCommercialRisksPopupService,
    GlossaryOfCommercialRisksResolvePagingParams, glossaryOfCommercialRisksRoute, GlossaryOfCommercialRisksService,
} from './';

const ENTITY_STATES = [
    ...glossaryOfCommercialRisksRoute,
    ...glossaryOfCommercialRisksPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RorumAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GlossaryOfCommercialRisksComponent,
        GlossaryOfCommercialRisksDetailComponent,
        GlossaryOfCommercialRisksDialogComponent,
        GlossaryOfCommercialRisksDeleteDialogComponent,
        GlossaryOfCommercialRisksPopupComponent,
        GlossaryOfCommercialRisksDeletePopupComponent,
    ],
    entryComponents: [
        GlossaryOfCommercialRisksComponent,
        GlossaryOfCommercialRisksDialogComponent,
        GlossaryOfCommercialRisksPopupComponent,
        GlossaryOfCommercialRisksDeleteDialogComponent,
        GlossaryOfCommercialRisksDeletePopupComponent,
    ],
    providers: [
        GlossaryOfCommercialRisksService,
        GlossaryOfCommercialRisksPopupService,
        GlossaryOfCommercialRisksResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumGlossaryOfCommercialRisksModule {}
