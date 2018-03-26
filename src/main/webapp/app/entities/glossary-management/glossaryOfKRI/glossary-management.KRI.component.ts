
import {Component, Input, OnDestroy, OnInit} from "@angular/core";
import {Principal, User} from "../../../shared";
import {JhiAlertService, JhiParseLinks} from "ng-jhipster";
import {ActivatedRoute} from "@angular/router";
import {GlossaryManagementService} from "../glossary-management.service";
import {GlossaryOfCommercialRisks} from "../../glossary-of-commercial-risks";
import {Message} from "primeng/components/common/api";
import {GlossaryOfKRI} from "../../glossary-of-KRI";

@Component({
    selector: 'glossary-management-KRI',
    templateUrl: './glossary-management.KRI.component.html',
    styleUrls: ['../glossary-management.component.css'],
})
export class GlossaryManagementKRIComponent implements OnInit, OnDestroy {
    @Input() user: User;
    msgs: Message[];
    // commercialRisks: ;
    KRIs: GlossaryOfKRI[];
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
            // console.log(hasAuthority)
            if (hasAuthority) {
                this.getAllUserKRI();
                this.createProposition = true;
            }
        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_LOCAL_ADMIN").then((hasAuthority: boolean) => {
            if (hasAuthority) {
                this.getAllKRI();
                this.createProposition = true;
            }

        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_GLOBAL_ADMIN").then((hasAuthority: boolean) => {
            if (hasAuthority) {
                this.getAllKRI();
                this.createProposition = true;
            }
        });
    }

    getAllKRI() {
        this.glossaryManagementService.getAllKRI().subscribe(
            (res) => {
                this.KRIs = res.json;
                this.showLoader = false;
            },
            (error) => {console.log(error)}
        );
    }
    createKRI() {
        this.createPropositionDialog = true;
    }

    getAllUserKRI() {
        this.glossaryManagementService.getAllUserKRI().subscribe(
            (res) => {
                this.KRIs = res.json;
                this.showLoader = false;
            },
            (error) => {console.log(error)}
        );
    }
}
