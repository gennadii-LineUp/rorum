import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {
    GlossaryOfPurposesComponent, GlossaryOfPurposesDeleteDialogComponent, GlossaryOfPurposesDeletePopupComponent,
    GlossaryOfPurposesDetailComponent, GlossaryOfPurposesDialogComponent, GlossaryOfPurposesPopupComponent,
    glossaryOfPurposesPopupRoute, GlossaryOfPurposesPopupService, GlossaryOfPurposesResolvePagingParams,
    glossaryOfPurposesRoute, GlossaryOfPurposesService,
} from './';
import {OrganizationChartModule} from "primeng/primeng";

const ENTITY_STATES = [
    ...glossaryOfPurposesRoute,
    ...glossaryOfPurposesPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        OrganizationChartModule
    ],
    declarations: [
        GlossaryOfPurposesComponent,
        GlossaryOfPurposesDetailComponent,
        GlossaryOfPurposesDialogComponent,
        GlossaryOfPurposesDeleteDialogComponent,
        GlossaryOfPurposesPopupComponent,
        GlossaryOfPurposesDeletePopupComponent,
    ],
    entryComponents: [
        GlossaryOfPurposesComponent,
        GlossaryOfPurposesDialogComponent,
        GlossaryOfPurposesPopupComponent,
        GlossaryOfPurposesDeleteDialogComponent,
        GlossaryOfPurposesDeletePopupComponent,
    ],
    providers: [
        GlossaryOfPurposesService,
        GlossaryOfPurposesPopupService,
        GlossaryOfPurposesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumGlossaryOfPurposesModule {}
