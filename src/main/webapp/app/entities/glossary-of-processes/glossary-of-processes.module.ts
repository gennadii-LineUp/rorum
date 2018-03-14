import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {
    GlossaryOfProcessesComponent, GlossaryOfProcessesDeleteDialogComponent, GlossaryOfProcessesDeletePopupComponent,
    GlossaryOfProcessesDetailComponent, GlossaryOfProcessesDialogComponent, GlossaryOfProcessesPopupComponent,
    glossaryOfProcessesPopupRoute, GlossaryOfProcessesPopupService, GlossaryOfProcessesResolvePagingParams,
    glossaryOfProcessesRoute, GlossaryOfProcessesService,
} from './';

const ENTITY_STATES = [
    ...glossaryOfProcessesRoute,
    ...glossaryOfProcessesPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GlossaryOfProcessesComponent,
        GlossaryOfProcessesDetailComponent,
        GlossaryOfProcessesDialogComponent,
        GlossaryOfProcessesDeleteDialogComponent,
        GlossaryOfProcessesPopupComponent,
        GlossaryOfProcessesDeletePopupComponent,
    ],
    entryComponents: [
        GlossaryOfProcessesComponent,
        GlossaryOfProcessesDialogComponent,
        GlossaryOfProcessesPopupComponent,
        GlossaryOfProcessesDeleteDialogComponent,
        GlossaryOfProcessesDeletePopupComponent,
    ],
    providers: [
        GlossaryOfProcessesService,
        GlossaryOfProcessesPopupService,
        GlossaryOfProcessesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumGlossaryOfProcessesModule {}
