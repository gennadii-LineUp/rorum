import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {Message} from 'primeng/components/common/api';
import {GlossaryOfProcesses} from "../../glossary-of-processes";
import {GlossaryManagementService} from "../glossary-management.service";
import {Principal, User} from "../../../shared";
import {GlossaryOfPurposes} from "../../glossary-of-purposes";
import {TreeNode} from "primeng/components/common/treenode";
import {error} from "util";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {GlossaryOfControlMechanisms} from "../../glossary-of-control-mechanisms";
import {showWarningOnce} from "tslint/lib/error";

@Component({
    selector: 'glossary-management-controlMechanisms',
    templateUrl: './glossary-management.control-mechanisms.component.html',
    styleUrls: ['../glossary-management.component.css'],
})
export class GlossaryManagementControlMechanismsComponent implements OnInit, OnDestroy {
    @Input() user: User;
    msgs: Message[];
    controlMechanisms: GlossaryOfControlMechanisms[];
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
        // this.initiateFormGroup();
        // this.buildTreeJson();
        // this.buildTreeJson2();
        if (this.user) {
            this.loadData();
        }

    }
    // initiateFormGroup() {
    //     this.purpose = new FormGroup({
    //         name: new FormControl("dupa duapa")
    //     });
    // }

    ngOnDestroy(): void {
    }

    loadData() {
        // console.log(this.user.authorities);
        this.principal.hasAuthority("ROLE_ZGLASZANIE_PROPOZYCJI_DO_SLOWNIKOW").then((hasAuthority: boolean) => {
            // console.log(hasAuthority)
            if (hasAuthority) {
                this.createProposition = true;
                // this.getAllAssignmentToCellOfCurrentOrganisation();
                console.log(this.user)
                this.showLoader = false;
            }
        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_LOCAL_ADMIN").then((hasAuthority: boolean) => {
            if (hasAuthority) {
                this.getAllControlMechanisms();
            }

        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_GLOBAL_ADMIN").then((hasAuthority: boolean) => {
            if (hasAuthority) {
                this.getAllControlMechanisms();
            }
        });
    }
    getAllControlMechanisms() {
            this.glossaryManagementService.getAllControlMechanisms().subscribe(
                (res) => {
                    this.controlMechanisms = res.json;
                    // console.log(this.purposes)
                    this.showLoader = false;
                },
                (error) => {console.log(error)}
            );
        }
    createControlMechanism() {
        this.createPropositionDialog = true;
    }
}
