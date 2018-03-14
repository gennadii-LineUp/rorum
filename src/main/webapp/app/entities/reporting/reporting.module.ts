import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import { ReportingComponent, reportingRoute, ReportingService } from './index';
import { HttpClient, HttpHandler } from '@angular/common/http';
import { ReportingOrderComponent } from './reporting-order.component';

const ENTITY_STATES = [
    ...reportingRoute
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReportingComponent,
        ReportingOrderComponent
    ],
    entryComponents: [
        ReportingComponent
    ],
    providers: [
        ReportingService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReportingModule {}
