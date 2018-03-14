import {Component, OnInit} from '@angular/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {Account, LoginModalService, LoginService, Principal, StateStorageService} from '../shared';
import {Router} from '@angular/router';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    username: any;
    authenticationError: boolean;
    password: string;
    rememberMe: boolean;

    constructor(
        private principal: Principal,
        private loginService: LoginService,
        private loginModalService: LoginModalService,
        private stateStorageService: StateStorageService,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        // console.log(this.account + ' ngOnInit home component');
        this.registerAuthenticationSuccess();
        // console.log(this.account.email);
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
        // console.log(this.account + ' registerAuthenticationSuccess home component');
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
       // this.modalRef = this.loginModalService.open();
        this.loginService.login({
            username: this.username,
            password: this.password,
            rememberMe: this.rememberMe
        }).then(() => {

            this.authenticationError = false;
            if (this.router.url === '/register' || (/^\/activate\//.test(this.router.url)) ||
                (/^\/reset\//.test(this.router.url))) {
                this.router.navigate(['']);
            }

            this.eventManager.broadcast({
                name: 'authenticationSucces',
                content: 'Sending Authentication Success'
            });

            const redirect = this.stateStorageService.getUrl();
            if (redirect) {
                this.stateStorageService.storeUrl(null);
                this.router.navigate([redirect]);
            }

        }).catch(() => {
            this.authenticationError = true;
        });
        if (this.account == null ) {
            this.registerAuthenticationSuccess();
        }
        // console.log(this.account + ' login home component');
        // this.eventManager.subscribe('authenticationSucces', (response) => this.ngOnInit());

    }

    changeError() {
        this.authenticationError = false;
    }
}
