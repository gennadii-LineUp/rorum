import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {Message} from 'primeng/components/common/api';
import {Principal, User, UserService} from "../../shared";
import {GlossaryOfProcesses} from "../glossary-of-processes";
import {GlossaryManagementService} from "./glossary-management.service";

@Component({
    selector: 'glossary-management',
    templateUrl: './glossary-management.component.html'
})
export class GlossaryManagementComponent implements OnInit, OnDestroy {
    activeTab: any = 0;
    msgs: Message[];
    user: User;
    processes: GlossaryOfProcesses[];
    typeOfUser: any;
    isUser: boolean;

    constructor(
        private activatedRoute: ActivatedRoute,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private glossaryManagementService: GlossaryManagementService,
        private principal: Principal,
    ) { }

    ngOnInit(): void {
        this.principal.identity().then( (user: User) => {
                this.user = user;
                this.loadData();
            }
        ).catch( (error) => {
                console.log('consumer error ' + error);
            }
        );

    }

    ngOnDestroy(): void {
    }

    showActiveTab() {
        console.log(this.activeTab);
    }


    onTabChange(event) {
        this.msgs = [];
        this.msgs.push( {
            severity:'info', summary:'Zmieniono zakładkę', detail: 'Index: ' + event.index
        });
        this.activeTab = event.index;
    }

    showParameters() {
        console.log(this.user);
    }
    loadData() {
        // console.log(this.user.authorities);
        this.principal.hasAuthority("ROLE_ZGLASZANIE_PROPOZYCJI_DO_SLOWNIKOW").then(
            (hasAuthority: boolean) => {
                if(hasAuthority) {
                    this.typeOfUser = "user";
                }
            });
        this.principal.hasAuthority(
            "ROLE_ZARZADZANIE_SLOWNIKAMI_LOCAL_ADMIN").then(
                (hasAuthority: boolean) => {
                    if(hasAuthority) {
                        this.typeOfUser = "local_admin";

                    }

                });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_GLOBAL_ADMIN").then(
            (hasAuthority: boolean) => {
                    if(hasAuthority) {
                        this.typeOfUser = "global_admin";
                    }
                });
    }


}
