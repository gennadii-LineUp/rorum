import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {
    OrganisationStructureComponent, OrganisationStructureDeleteDialogComponent,
    OrganisationStructureDeletePopupComponent, OrganisationStructureDetailComponent,
    OrganisationStructureDialogComponent, OrganisationStructurePopupComponent, organisationStructurePopupRoute,
    OrganisationStructurePopupService, OrganisationStructureResolvePagingParams, organisationStructureRoute,
    OrganisationStructureService,
} from './';

const ENTITY_STATES = [
    ...organisationStructureRoute,
    ...organisationStructurePopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrganisationStructureComponent,
        OrganisationStructureDetailComponent,
        OrganisationStructureDialogComponent,
        OrganisationStructureDeleteDialogComponent,
        OrganisationStructurePopupComponent,
        OrganisationStructureDeletePopupComponent,
    ],
    entryComponents: [
        OrganisationStructureComponent,
        OrganisationStructureDialogComponent,
        OrganisationStructurePopupComponent,
        OrganisationStructureDeleteDialogComponent,
        OrganisationStructureDeletePopupComponent,
    ],
    providers: [
        OrganisationStructureService,
        OrganisationStructurePopupService,
        OrganisationStructureResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumOrganisationStructureModule {}
