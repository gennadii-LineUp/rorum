import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {Principal} from "../../shared";

@Component({
    selector: 'jhi-glossary-of-KRI',
    templateUrl: './glossary-of-KRI.component.html'
})
export class GlossaryOfKRIComponent implements OnInit, OnDestroy {

    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) { }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });

    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

}
