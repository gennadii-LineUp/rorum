import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {DatePipe} from '@angular/common';

import {
    AccountService, AuthServerProvider, CSRFService, HasAnyAuthorityDirective, JhiLoginModalComponent,
    LoginModalService, LoginService, Principal, RorumSharedCommonModule, RorumSharedLibsModule, StateStorageService,
    UserService,
} from './';

@NgModule({
    imports: [
        RorumSharedLibsModule,
        RorumSharedCommonModule
    ],
    declarations: [
        JhiLoginModalComponent,
        HasAnyAuthorityDirective
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        DatePipe
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        RorumSharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class RorumSharedModule {}
