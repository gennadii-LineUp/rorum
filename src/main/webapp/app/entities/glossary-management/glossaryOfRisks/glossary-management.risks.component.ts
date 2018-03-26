import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {Message} from 'primeng/components/common/api';
import {GlossaryOfProcesses} from "../../glossary-of-processes";
import {GlossaryManagementService} from "../glossary-management.service";
import {Principal, User} from "../../../shared";
import {GlossaryOfRisks} from "../../glossary-of-risks";
import {MessageService} from "primeng/components/common/messageservice";

@Component({
    selector: 'glossary-management-risks',
    templateUrl: './glossary-management.risks.component.html',
    styleUrls: ['../glossary-management.component.css'],
})
export class GlossaryManagementRisksComponent implements OnInit, OnDestroy {
    @Input() user: User;
    msgs: Message[];
    risks: GlossaryOfRisks[];
    createProposition = false;
    createPropositionDialog = false;
    showLoader = true;

    constructor(
        private activatedRoute: ActivatedRoute,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private glossaryManagementService: GlossaryManagementService,
        private principal: Principal,
        private messageService: MessageService,
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
                this.showLoader = false;
                this.getAllUserRisks();
                this.createProposition = true;
            }
        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_LOCAL_ADMIN").then((hasAuthority: boolean) => {
            if (hasAuthority) {
                this.getAllRisks();
            }

        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_GLOBAL_ADMIN").then((hasAuthority: boolean) => {
            if (hasAuthority) {
                this.getAllRisks();
            }
        });
    }

    getAllRisks() {
        this.glossaryManagementService.getAllRisks().subscribe(
            (res) => {
                this.risks = res.json;
                this.showLoader = false;
            },
            (error) => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Błąd pobierania danych',
                    detail: 'Wystąpił błąd przy pobieraniu danych, skontaktuj się z administratorem./// ' + error
                });
                console.log(error)
            }
        );
    }
    getAllUserRisks() {
        this.glossaryManagementService.getAllUserRisks().subscribe(
            (res) => {
                this.risks = res.json;
                this.showLoader = false;
            },
            (error) => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Błąd pobierania danych',
                    detail: 'Wystąpił błąd przy pobieraniu danych, skontaktuj się z administratorem./// ' + error
                });
                console.log(error)
            }
        );
    }
    createProcess() {
        this.createPropositionDialog = true;
    }
}
