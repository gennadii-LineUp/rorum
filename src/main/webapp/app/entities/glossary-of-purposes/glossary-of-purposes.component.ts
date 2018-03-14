import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';

import {GlossaryOfPurposes} from './glossary-of-purposes.model';
import {GlossaryOfPurposesService} from './glossary-of-purposes.service';
import {ITEMS_PER_PAGE, Principal, ResponseWrapper} from '../../shared';
import {TreeNode} from "primeng/primeng";
import {isNull} from "util";

@Component({
    selector: 'jhi-glossary-of-purposes',
    templateUrl: './glossary-of-purposes.component.html'
})
export class GlossaryOfPurposesComponent implements OnInit, OnDestroy {

    currentAccount: any;
    glossaryOfPurposes: GlossaryOfPurposes[];
    allGlossaryOfPurposes: GlossaryOfPurposes[];
    mainGlossaries: GlossaryOfPurposes[] = [];
    data: TreeNode[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(private glossaryOfPurposesService: GlossaryOfPurposesService,
                private parseLinks: JhiParseLinks,
                private jhiAlertService: JhiAlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    loadAll() {
        this.glossaryOfPurposesService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.glossaryOfPurposesService.queryAll().subscribe(
            (res: ResponseWrapper) => this.onAllSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/glossary-of-purposes'], {
            queryParams:
                {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
                }
        }).then(() => {
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/glossary-of-purposes', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }

    loadNodeTree(glossaryOfPurposes) {
        this.data = [{
            label: 'Słownik celów'
            ,
            children: [{
                label: '',
                children: [{

                }]
            }
            ]
        }];

        // this.data[0].children[0].label = 'AAAA';

        // let treeNodeModel: TreeNode = {
        //     label: 'Słownik Celów',
        //     children: []
        // };
        for (let i = 0, n = glossaryOfPurposes.length; i < n; i++) {
            if (isNull(glossaryOfPurposes[i].parentId)) {
                this.mainGlossaries.push(glossaryOfPurposes[i]);
                // children.push({"label": glossaryOfPurposes[i].name})
                // console.log(glossaryOfPurposes.indexOf(glossaryOfPurposes[i]));
                // let treeNode: TreeNode = {
                //     label: glossaryOfPurposes[i].name,
                //     children: []
                // };
                // treeNodeModel.children.push(treeNode);
                // console.log(treeNode)
                this.data[0].label = this.glossaryOfPurposes[i].name;
            } else if (!isNull(this.glossaryOfPurposes[i].parentId)) {
                for(let j = 0, n = this.mainGlossaries.length; j < n; j++ ){
                    if(glossaryOfPurposes[i].parentId === this.mainGlossaries[j].id) {
                        this.data[0].children[i].label = this.glossaryOfPurposes[i].name;
                        // treeNodeModel.children.push(treeNode);
                    }
                }
            }
        }
            // for(let j = 0, n = this.mainGlossaries.length; j < n; j++ ){
            //     if(glossaryOfPurposes[i].parentId === this.mainGlossaries[j].id) {
            //         let treeNode: TreeNode = {
            //             label: glossaryOfPurposes[i].name,
            //             children: []
            //         };
            //         treeNodeModel.children.push(treeNode);
            //     }
            // }
        // }
        // this.data.push(treeNodeModel);
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGlossaryOfPurposes();
        // this.data = [];
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: GlossaryOfPurposes) {
        return item.id;
    }

    registerChangeInGlossaryOfPurposes() {
        this.eventSubscriber = this.eventManager.subscribe('glossaryOfPurposesListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.glossaryOfPurposes = data;
    }

    private onAllSuccess(data, headers) {
        this.allGlossaryOfPurposes = data;
        this.loadNodeTree(data);
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
