import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager, JhiLanguageService} from 'ng-jhipster';

import {ProfileService} from '../profiles/profile.service';
import {Account, JhiLanguageHelper, LoginModalService, LoginService, Principal} from '../../shared';

import {VERSION} from '../../app.constants';
import { OrganisationStructure, OrganisationStructureService } from '../../entities/organisation-structure';
import { Observable } from 'rxjs/Observable';

// import { SidebarSharedService } from '../../shared/sidebarShared.service';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: [
        'navbar.css'
    ]
})
export class NavbarComponent implements OnInit {

    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    account: Account;
    organisationStructureName: String;

    constructor(
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router,
        private eventManager: JhiEventManager,
        private organisationStructureService: OrganisationStructureService
        // private sidebarShareService: SidebarSharedService
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().subscribe((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
        this.principal.identity().then((account) => {
            this.account = account;
            if (this.account) {
                this.organisationStructureService.find(this.account.organisationStructureId).subscribe(
                    (organisationStructure) => {
                        this.organisationStructureName = organisationStructure.name;
                    }
                );
            }
            this.eventManager.subscribe('authenticationSucces', (response) => this.ngOnInit());
        });
   }

    changeLanguage(languageKey: string) {
      this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
        this.principal.identity().then((account) => {
            this.account = account;
            // console.log(this.account);
        });
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }

}
