import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {Message} from 'primeng/components/common/api';
import {GlossaryOfProcesses} from "../../glossary-of-processes";
import {GlossaryManagementService} from "../glossary-management.service";
import {Principal, User} from "../../../shared";
import {GlossaryOfMeasureUnits} from "../../glossary-of-measure-units";
import {GlossaryOfCommercialRisks} from "../../glossary-of-commercial-risks";

@Component({
    selector: 'glossary-management-corruptionRisks',
    templateUrl: './glossary-management.corruptionRisks.component.html',
    styleUrls: ['../glossary-management.component.css'],
})
export class GlossaryManagementCorruptionRisksComponent implements OnInit, OnDestroy {
    @Input() user: User;
    msgs: Message[];
    commercialRisks: GlossaryOfCommercialRisks[];
    createPropositionDialog = false;
    createProposition = false;
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
                this.getAllUserCommercialRisks();
            }
        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_LOCAL_ADMIN").then((hasAuthority: boolean) => {
            if (hasAuthority) {
                this.getAllCommercialRisks();
            }

        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_GLOBAL_ADMIN").then((hasAuthority: boolean) => {
            if (hasAuthority) {
                this.getAllCommercialRisks();
            }
        });
    }

    getAllCommercialRisks() {
        this.glossaryManagementService.getAllCommercialRisks().subscribe(
            (res) => {
                this.commercialRisks = res.json;
                this.showLoader = false;
            },
            (error) => {console.log(error)}
        );
    }
    createCommercialRisk() {
        this.createPropositionDialog = true;
    }
    getAllUserCommercialRisks() {
        this.glossaryManagementService.getAllUserCommercialRisks().subscribe(
            (res) => {
                this.commercialRisks = res.json;
                this.showLoader = false;
            },
            (error) => {console.log(error)}
        );
    }
}
