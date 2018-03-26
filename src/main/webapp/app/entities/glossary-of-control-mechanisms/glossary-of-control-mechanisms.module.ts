import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {glossaryOfControlMechanismsRoute} from "./glossary-of-control-mechanisms.route";
import {GlossaryOfControlMechanismsComponent} from "./glossary-of-control-mechanisms.component";
import {GlossaryOfControlMechanismsService} from "./glossary-of-control-mechanisms.service";

const ENTITY_STATES = [
    ...glossaryOfControlMechanismsRoute
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GlossaryOfControlMechanismsComponent
    ],
    entryComponents: [
        GlossaryOfControlMechanismsComponent
    ],
    providers: [
        GlossaryOfControlMechanismsService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumGlossaryOfKRIModule {}
