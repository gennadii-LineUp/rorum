import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RorumSharedModule} from '../../shared';
import {glossaryOfKRIRoute} from "./glossary-of-KRI.route";
import {GlossaryOfKRIService} from "./glossary-of-KRI.service";
import {GlossaryOfKRIComponent} from "./glossary-of-KRI.component";

const ENTITY_STATES = [
    ...glossaryOfKRIRoute
];

@NgModule({
    imports: [
        RorumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GlossaryOfKRIComponent
    ],
    entryComponents: [
        GlossaryOfKRIComponent
    ],
    providers: [
        GlossaryOfKRIService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RorumGlossaryOfKRIModule {}
