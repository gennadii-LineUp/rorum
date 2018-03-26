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
import {MessageService} from "primeng/components/common/messageservice";

@Component({
    selector: 'glossary-management-purposes',
    templateUrl: './glossary-management.purposes.component.html',
    styleUrls: ['../glossary-management.component.css'],
})
export class GlossaryManagementPurposesComponent implements OnInit, OnDestroy {
    @Input() user: User;
    msgs: Message[];
    purposes: GlossaryOfPurposes[];
    data: TreeNode[];
    test: TreeNode[];
    createProposition = false;
    createPropositionDialog = false;
    showLoader = true;
    // purpose: FormGroup;

    constructor(
        private activatedRoute: ActivatedRoute,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private glossaryManagementService: GlossaryManagementService,
        private principal: Principal,
        private messageService: MessageService,
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
                this.getAllAssignmentToCellOfCurrentOrganisation();
                console.log(this.user)
            }
        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_LOCAL_ADMIN").then((hasAuthority: boolean) => {
            if (hasAuthority) {
                this.getAllPurposes();
            }

        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_GLOBAL_ADMIN").then((hasAuthority: boolean) => {
            if (hasAuthority) {
                this.getAllPurposes();
            }
        });
    }
    createPurposeProposition() {
        this.createPropositionDialog = true;
        console.log("create proposition");
        this.showWarn();
    }

    getAllPurposes() {
        this.glossaryManagementService.getAllPurposes().subscribe(
            (res) => {
                this.purposes = res.json;
                // console.log(this.purposes)
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
    getAllAssignmentToCellOfCurrentOrganisation() {
        this.glossaryManagementService.getAllAssignmentToCellOfCurrentOrganisation().subscribe(
            (res) => {
                this.purposes = res.json;
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

    showWarn() {
        this.msgs = [];
        this.msgs.push({severity: 'warn', summary: 'Uwaga!', detail: 'Funkcjonalność nie została jeszcze przygotowana'});
    }
    showSuccess() {
        this.msgs = [];
        this.msgs.push({severity: 'success', summary: 'Sukces!', detail: 'Wszystko ok'});
    }
    showError() {
        this.msgs = [];
        this.msgs.push({severity: 'error', summary: 'Błąd', detail: 'Wystąpił problem'});
    }

    onSubmit(gop: FormGroup) {
        console.log("onSubmit func")
    }
}
