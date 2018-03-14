import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RorumSharedModule } from '../../shared';
import {
    IncidentService,
    IncidentPopupService,
    IncidentComponent,
    IncidentDetailComponent,
    IncidentDialogComponent,
    IncidentPopupComponent,
    IncidentDeletePopupComponent,
    IncidentDeleteDialogComponent,
    incidentRoute,
    incidentPopupRoute,
    incidentUserRoute,
    IncidentUserComponent,
    IncidentUserOrderComponent,
    IncidentOrderComponent
} from './';
import {CheckboxModule} from 'primeng/components/checkbox/checkbox';
import {ReactiveFormsModule} from "@angular/forms";
import {MessageModule} from "primeng/primeng";
import {MessagesModule} from 'primeng/components/messages/messages';
import {MessageService} from "primeng/components/common/messageservice";

const ENTITY_STATES = [
    ...incidentRoute,
    ...incidentPopupRoute,
    ...incidentUserRoute
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        CheckboxModule,
        ReactiveFormsModule,
        MessagesModule,
        MessageModule
    ],
    declarations: [
        IncidentComponent,
        IncidentDetailComponent,
        IncidentDialogComponent,
        IncidentDeleteDialogComponent,
        IncidentPopupComponent,
        IncidentDeletePopupComponent,
        IncidentUserComponent,
        IncidentUserOrderComponent,
        IncidentOrderComponent
    ],
    entryComponents: [
        IncidentComponent,
        IncidentDialogComponent,
        IncidentPopupComponent,
        IncidentDeleteDialogComponent,
        IncidentDeletePopupComponent,
        IncidentUserComponent,
        IncidentUserOrderComponent,
        IncidentOrderComponent
    ],
    providers: [
        IncidentService,
        IncidentPopupService,
        MessageService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumIncidentModule {}
