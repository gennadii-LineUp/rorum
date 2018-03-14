import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {RorumAdminModule} from '../../admin/admin.module';
import {
    SetOfSentPurposesComponent, SetOfSentPurposesDeleteDialogComponent, SetOfSentPurposesDeletePopupComponent,
    SetOfSentPurposesDetailComponent, SetOfSentPurposesDialogComponent, SetOfSentPurposesPopupComponent,
    setOfSentPurposesPopupRoute, SetOfSentPurposesPopupService, SetOfSentPurposesResolvePagingParams,
    setOfSentPurposesRoute, SetOfSentPurposesService,
} from './';
import {SetOfSentPurposesCheckComponent} from './set-of-sent-purposes-check.component';
import {DatepickerFrontViewAdminPipe} from '../orders/pipes/datepickerFrontViewAdmin.pipe';
import {OrderCelePopupCheckboxesComponent} from '../orders/order-cele-p-chbs.component';
import {RorumOrdersModule} from '../orders/orders.module';
import {PurposeAccomplishmentPipe} from "./pipes/purposeAccomplishment.pipe";
import {ReactionOnRiskPipe} from "./pipes/reactionOnRisk.pipe";

const ENTITY_STATES = [
    ...setOfSentPurposesRoute,
    ...setOfSentPurposesPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RorumAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        RorumOrdersModule,
    ],
    declarations: [
        SetOfSentPurposesComponent,
        SetOfSentPurposesDetailComponent,
        SetOfSentPurposesDialogComponent,
        SetOfSentPurposesDeleteDialogComponent,
        SetOfSentPurposesPopupComponent,
        SetOfSentPurposesDeletePopupComponent,
        SetOfSentPurposesCheckComponent,
        DatepickerFrontViewAdminPipe,
        PurposeAccomplishmentPipe,
        ReactionOnRiskPipe
    ],
    entryComponents: [
        SetOfSentPurposesComponent,
        SetOfSentPurposesDialogComponent,
        SetOfSentPurposesPopupComponent,
        SetOfSentPurposesDeleteDialogComponent,
        SetOfSentPurposesDeletePopupComponent,
        SetOfSentPurposesCheckComponent
    ],
    providers: [
        SetOfSentPurposesService,
        SetOfSentPurposesPopupService,
        SetOfSentPurposesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumSetOfSentPurposesModule {}
