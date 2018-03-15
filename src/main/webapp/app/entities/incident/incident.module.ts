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
    IncidentUserOrderComponent,
    IncidentOrderComponent
} from './';
import {CheckboxModule} from 'primeng/components/checkbox/checkbox';
import {ReactiveFormsModule} from "@angular/forms";
import {GrowlModule} from "primeng/primeng";
import {MessageService} from "primeng/components/common/messageservice";
import {Autosize} from "../../shared/directives/autosize.directive";
import {FilterPipe} from "./filter/filter.pipe";
import {StatusOfIncidentPipe} from "./pipes/statusOfIncident.pipe";
import {IsCriticalPipe} from "./pipes/isCritical.pipe";
import {IncidentUserAllComponent} from "./incident-user/incident-user-all.component";

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
        GrowlModule
    ],
    declarations: [
        IncidentComponent,
        IncidentDetailComponent,
        IncidentDialogComponent,
        IncidentDeleteDialogComponent,
        IncidentPopupComponent,
        IncidentDeletePopupComponent,
        IncidentUserOrderComponent,
        IncidentUserAllComponent,
        IncidentOrderComponent,
        FilterPipe,
        StatusOfIncidentPipe,
        IsCriticalPipe,
		Autosize,
    ],
    entryComponents: [
        IncidentComponent,
        IncidentDialogComponent,
        IncidentPopupComponent,
        IncidentDeleteDialogComponent,
        IncidentDeletePopupComponent,
        IncidentUserOrderComponent,
        IncidentUserAllComponent,
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
