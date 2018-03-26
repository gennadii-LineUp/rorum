import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {Message} from 'primeng/components/common/api';
import {GlossaryOfProcesses} from "../../glossary-of-processes";
import {GlossaryManagementService} from "../glossary-management.service";
import {Principal, User} from "../../../shared";

@Component({
    selector: 'glossary-management-processes',
    templateUrl: './glossary-management.processes.component.html',
    styleUrls: ['../glossary-management.component.css'],
})
export class GlossaryManagementProcessesComponent implements OnInit, OnDestroy {
    @Input() user: User;
    msgs: Message[];
    processes: GlossaryOfProcesses[];
    createProposition = false;
    createPropositionDialog = false;
    showLoader = true;

    constructor(
        private activatedRoute: ActivatedRoute,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private glossaryManagementService: GlossaryManagementService,
        private principal: Principal,
    ) { }

    ngOnInit(): void {
        if (this.user) {
            this.loadData();
        }

    }

    ngOnDestroy(): void {
    }

    loadData() {
        // console.log(this.user.authorities);
        this.principal.hasAuthority("ROLE_ZGLASZANIE_PROPOZYCJI_DO_SLOWNIKOW").then((hasAuthority: boolean) => {
            if (hasAuthority) {
                console.log(hasAuthority)
                this.showLoader = false;
            }
        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_LOCAL_ADMIN").then((hasAuthority: boolean) => {
            if (hasAuthority) {
                this.getAllProcesses();
                this.createProposition = true;
            }

        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_GLOBAL_ADMIN").then((hasAuthority: boolean) => {
            if (hasAuthority) {
                this.getAllProcesses();
                this.createProposition = true;
            }
        });
    }

    getAllProcesses() {
        this.glossaryManagementService.getAllProcesses().subscribe(
            (res) => {
                this.processes = res.json;
                this.showLoader = false;
            },
            (error) => {console.log(error)}
        );
    }
    createProcess() {
        this.createPropositionDialog = true;
    }
}
