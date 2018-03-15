import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {Message} from 'primeng/components/common/api';
import {GlossaryOfProcesses} from "../../glossary-of-processes";
import {GlossaryManagementService} from "../glossary-management.service";
import {Principal, User} from "../../../shared";
import {GlossaryOfMeasureUnits} from "../../glossary-of-measure-units";

@Component({
    selector: 'glossary-management-measures',
    templateUrl: './glossary-management.measures.component.html'
})
export class GlossaryManagementMeasuresComponent implements OnInit, OnDestroy {
    @Input() user: User;
    msgs: Message[];
    measures: GlossaryOfMeasureUnits[];

    constructor(
        private activatedRoute: ActivatedRoute,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private glossaryManagementService: GlossaryManagementService,
        private principal: Principal,
    ) { }

    ngOnInit(): void {
        if(this.user) {
            this.loadData();
        }

    }

    ngOnDestroy(): void {
    }

    loadData() {
        // console.log(this.user.authorities);
        this.principal.hasAuthority("ROLE_ZGLASZANIE_PROPOZYCJI_DO_SLOWNIKOW").then((hasAuthority: boolean) => {
            // console.log(hasAuthority)
        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_LOCAL_ADMIN").then((hasAuthority: boolean) => {
            if(hasAuthority){
                this.getAllMeasures();
            }

        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_GLOBAL_ADMIN").then((hasAuthority: boolean) => {
            if(hasAuthority){
                this.getAllMeasures();
            }
        });
    }

    getAllMeasures() {
        this.glossaryManagementService.getAllMeasures().subscribe(
            (res) => {
                this.measures = res.json;
            },
            (error) => {console.log(error)}
        );
    }
}
