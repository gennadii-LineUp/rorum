import {AfterViewInit, Component, ElementRef, OnDestroy, OnInit, Renderer} from '@angular/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiLanguageService} from 'ng-jhipster';

import {Register} from './register.service';
import {EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE, LoginModalService, ResponseWrapper} from '../../shared';
import {OrganisationStructure, OrganisationStructureService} from '../../entities/organisation-structure';
import 'rxjs/add/operator/takeWhile';
import {Router} from '@angular/router';

@Component({
    selector: 'jhi-register',
    templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit, AfterViewInit, OnDestroy {
    confirmPassword: string;
    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    registerAccount: any;
    success: boolean;
    modalRef: NgbModalRef;
    alive = true;
    organisations: Array<OrganisationStructure>;
    active_organization = '';
    select_organization_disabled = true;
    organisationStructureId: number;

    constructor(
        private languageService: JhiLanguageService,
        private loginModalService: LoginModalService,
        private registerService: Register,
        private organisationStructureService: OrganisationStructureService,
        private elementRef: ElementRef,
        private jhiAlertService: JhiAlertService,
        private router: Router,
        private renderer: Renderer
    ) {
    }

    ngOnInit() {
        this.select_organization_disabled = true;
        this.success = false;
        this.registerAccount = {};
        this.organisationStructureService.getAllOrganizations()
            .takeWhile(() => this.alive)
            .subscribe(
                (res: any) => {
                    if (res.json.length > 0) {
                        this.select_organization_disabled = false;
                        this.organisations = res.json;
                        console.log(this.organisations);
                    }
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    ngOnDestroy() {
        this.alive = false;
    }

    ngAfterViewInit() {
        this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []);
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    registerNew() {
        if (this.registerAccount.password !== this.confirmPassword) {
            this.doNotMatch = 'ERROR';
        } else if (this.organisationStructureId && this.registerAccount.email && this.registerAccount.password
                    && this.registerAccount.firstName && this.registerAccount.lastName) {
            this.registerAccount.login = this.registerAccount.email;
            console.log(this.registerAccount);
            this.doNotMatch = null;
            this.error = null;
            this.errorUserExists = null;
            this.errorEmailExists = null;
            this.languageService.getCurrent().then((key) => {
                this.registerAccount.langKey = key;
                this.registerService.save(this.registerAccount, this.organisationStructureId).subscribe((res: any) => {
                    console.log(res);
                    this.success = true;
                }, (response) => this.processError(response));
            });
        }
    }

    openLogin() {
        this.modalRef = this.loginModalService.open();
    }

    private processError(response) {
        this.success = null;
        if (response.status === 400 && response.json().type === LOGIN_ALREADY_USED_TYPE) {
            this.errorUserExists = 'ERROR';
        } else if (response.status === 400 && response.json().type === EMAIL_ALREADY_USED_TYPE) {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }

    selectOrganizationFunction(field: string) {
        console.log('-------- ' + field + ':');
        this.organisationStructureId = +field;

    }
}
