import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {RorumAdminModule} from '../../admin/admin.module';
import {
    GlossaryOfRisksComponent, GlossaryOfRisksDeleteDialogComponent, GlossaryOfRisksDeletePopupComponent,
    GlossaryOfRisksDetailComponent, GlossaryOfRisksDialogComponent, GlossaryOfRisksPopupComponent,
    glossaryOfRisksPopupRoute, GlossaryOfRisksPopupService, GlossaryOfRisksResolvePagingParams, glossaryOfRisksRoute,
    GlossaryOfRisksService,
} from './';

const ENTITY_STATES = [
    ...glossaryOfRisksRoute,
    ...glossaryOfRisksPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RorumAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GlossaryOfRisksComponent,
        GlossaryOfRisksDetailComponent,
        GlossaryOfRisksDialogComponent,
        GlossaryOfRisksDeleteDialogComponent,
        GlossaryOfRisksPopupComponent,
        GlossaryOfRisksDeletePopupComponent,
    ],
    entryComponents: [
        GlossaryOfRisksComponent,
        GlossaryOfRisksDialogComponent,
        GlossaryOfRisksPopupComponent,
        GlossaryOfRisksDeleteDialogComponent,
        GlossaryOfRisksDeletePopupComponent,
    ],
    providers: [
        GlossaryOfRisksService,
        GlossaryOfRisksPopupService,
        GlossaryOfRisksResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumGlossaryOfRisksModule {}
