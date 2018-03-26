import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {
    OrdersComponent, OrdersDeleteDialogComponent, OrdersDeletePopupComponent, OrdersDetailComponent,
    OrdersDialogComponent, OrdersPopupComponent, ordersPopupRoute, OrdersPopupService, OrdersResolvePagingParams,
    ordersRoute, OrdersService,
} from './';
import {OrderCeleComponent} from './order-cele.component';
import {OrdersUserComponent} from './orders-user.component';
import {OrderCelePopupCheckboxesComponent} from './order-cele-p-chbs.component';
import {KryteriumPipe} from './pipes/kryterium.pipe';
import {DatepickerFrontViewUserPipe} from './pipes/datepickerFrontViewUser.pipe';
import {OrderCelePopupBadRiskComponent} from './order-cele-p-bad-risk.component';
import {ProbabilityValuePipe} from './pipes/probability-value.pipe';
import {InfluenceValuePipe} from './pipes/influence-value.pipe';
import {TrueFalseFrontViewPipe} from "./pipes/trueFalseFrontView.pipe";
import {HighriskAnalizFrontViewPipe} from "./pipes/highriskAnalizFrontView.pipe";
import {PurposeAccomplishmentPipe} from "./pipes/purposeAccomplishment.pipe";
import {ReactionOnRiskPipe} from "./pipes/reactionOnRisk.pipe";

const ENTITY_STATES = [
    ...ordersRoute,
    ...ordersPopupRoute,
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrdersComponent,
        OrdersDetailComponent,
        OrdersDialogComponent,
        OrdersDeleteDialogComponent,
        OrdersPopupComponent,
        OrdersDeletePopupComponent,
        OrderCeleComponent,
        OrdersUserComponent,
        OrderCelePopupCheckboxesComponent, OrderCelePopupBadRiskComponent,
        KryteriumPipe, DatepickerFrontViewUserPipe, ProbabilityValuePipe, InfluenceValuePipe, TrueFalseFrontViewPipe,
        HighriskAnalizFrontViewPipe,
        PurposeAccomplishmentPipe,
        ReactionOnRiskPipe
    ],
    exports: [
        OrderCelePopupCheckboxesComponent, OrderCelePopupBadRiskComponent,
        KryteriumPipe, DatepickerFrontViewUserPipe, ProbabilityValuePipe, InfluenceValuePipe, TrueFalseFrontViewPipe,
        HighriskAnalizFrontViewPipe,
        PurposeAccomplishmentPipe,
        ReactionOnRiskPipe
    ],
    entryComponents: [
        OrdersComponent,
        OrdersDialogComponent,
        OrdersPopupComponent,
        OrdersDeleteDialogComponent,
        OrdersDeletePopupComponent,
        OrderCeleComponent,
        OrdersUserComponent
    ],
    providers: [
        OrdersService,
        OrdersPopupService,
        OrdersResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumOrdersModule {}
