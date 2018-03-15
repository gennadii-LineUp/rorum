import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {Message} from 'primeng/components/common/api';
import {GlossaryOfProcesses} from "../../glossary-of-processes";
import {GlossaryManagementService} from "../glossary-management.service";
import {Principal, User} from "../../../shared";
import {GlossaryOfPurposes} from "../../glossary-of-purposes";
import {TreeNode} from "primeng/components/common/treenode";


@Component({
    selector: 'glossary-management-purposes',
    templateUrl: './glossary-management.purposes.component.html'
})
export class GlossaryManagementPurposesComponent implements OnInit, OnDestroy {
    @Input() user: User;
    msgs: Message[];
    purposes: GlossaryOfPurposes[];
    data: TreeNode[];
    test: TreeNode[];

    constructor(
        private activatedRoute: ActivatedRoute,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private glossaryManagementService: GlossaryManagementService,
        private principal: Principal,
    ) { }

    ngOnInit(): void {
        // this.buildTreeJson();
        // this.buildTreeJson2();
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
                this.getAllPurposes();
            }

        });
        this.principal.hasAuthority("ROLE_ZARZADZANIE_SLOWNIKAMI_GLOBAL_ADMIN").then((hasAuthority: boolean) => {
            if(hasAuthority){
                this.getAllPurposes();
            }
        });
    }

    getAllPurposes() {
        this.glossaryManagementService.getAllPurposes().subscribe(
            (res) => {
                this.purposes = res.json;
                console.log(this.purposes)
            },
            (error) => {console.log(error)}
        );
    }
    //
    // buildTreeJson() {
    //     this.data = [{
    //         label: 'Root',
    //         children: [
    //             {
    //                 label: 'Child 1',
    //                 children: [
    //                     {
    //                         label: 'Grandchild 1.1', type: 'leaf'
    //                     },
    //                     {
    //                         label: 'Grandchild 1.2', type: 'leaf'
    //                     }
    //                 ]
    //             },
    //             {
    //                 label: 'Child 2',
    //                 children: [
    //                     {
    //                         label: 'Child 2.1', type: 'leaf'
    //                     },
    //                     {
    //                         label: 'Child 2.2', type: 'leaf'
    //                     }
    //                 ]
    //             }
    //         ]
    //     }];
    // }
    // buildTreeJson2() {
    //     this.test = [{
    //
    //     }]
    // }
    // findByParentId() {
    //
    // }
}
