import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import { ReportingService } from '.';
import { Http } from '@angular/http';
import { SERVER_API_URL } from '../../app.constants';
import { ResponseWrapper, ITEMS_PER_PAGE, Principal } from '../../shared';
import { Orders } from '../orders';
import {Message} from 'primeng/components/common/api';
import { ConfirmationService } from 'primeng/components/common/confirmationservice';
import { GlossaryOfProcesses } from '../glossary-of-processes';
import { OrganisationStructure } from '../organisation-structure';

@Component({
    selector: 'reporting-order',
    templateUrl: './reporting-order.component.html',
    styleUrls: [ './reporting-order.component.css'],
})
export class ReportingOrderComponent implements OnInit, OnDestroy {
    msgs: Message[] = [];
    isActive: boolean;
    orderId: number;
    orderName: String;
    userId: number;
    order: Orders;
    isAvailable = true;
    account: Account;
    reportByProcessDialog = false;
    glossaryOfProceeses: GlossaryOfProcesses[];
    chosenProcessId: any;
    chosenOrganisationStructureId: number;
    changesInRisks = false;
    organisationStructures: OrganisationStructure[];
    purposeAccomplishmentReportAdmin = false;

    constructor(
        private activatedRoute: ActivatedRoute,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private reportingService: ReportingService,
        private principal: Principal,
    ) { }

    ngOnInit(): void {
        this.isActive = false;
        // this.loadAll();
        // console.log("ngOnInit");
        // console.log('OK');
        // this.loadAll();
        this.activatedRoute.params.subscribe(
            (param) => {
                this.orderId = param['orderId'];
                this.reportingService.loadOrder(this.orderId).subscribe(
                    (res) => {
                        this.order = res.json
                        // console.log(this.order);
                    },
                    (error) => {console.error('Problem getting Order: ' + error)},
                    () => {}
                );
            },
            (error) => {console.error("Nieprawidłowe przypisanie parametru - orderId")}
        );
        this.principal.identity().then( (account: Account) => {
            this.userId = +account.id;
            this.account = account;
            // console.log(this.account);
            }
        ).catch( (error) => {
            console.log('consumer error ' + error);
            }
        );
    }
    reportByProcessDialogBox() {
        this.reportingService.getAllProcesses(this.orderId).subscribe(
            (res) => {
                this.glossaryOfProceeses = res.json;
                // console.log(this.glossaryOfProceeses);
            },
            (error) => {console.error(error)}
        );
        this.reportByProcessDialog = true;
    }

    ngOnDestroy(): void {
        // console.log("ngOnDestroy");
    }

    showWarn() {
        this.msgs = [];
        this.msgs.push({severity: 'warn', summary: 'Uwaga!', detail: 'Funkcjonalność nie została jeszcze przygotowana'});
    }
    showSuccess() {
        this.msgs = [];
        this.msgs.push({severity: 'success', summary: 'Sukces!', detail: 'Wygenerowano raport'});
    }
    showError() {
        this.msgs = [];
        this.msgs.push({severity: 'error', summary: 'Błąd', detail: 'Problem z wygenerowaniem raportu. Proszę skontaktować się z administratorem'});
    }
    showSingleReportError() {
        this.msgs = [];
        this.msgs.push({severity: 'error', summary: 'Błąd', detail: 'Problem z wygenerowaniem raportu. Możliwe, że wybrana jednostka nie wypełniła planu.'});
    }

    generatePurposeAccomplishmentRaport(orderId: number, userId: number, filename: String) {
        console.log(filename);
        this.isAvailable = false;
        // const filename: String = "123" + Date.now().toLocaleString;
        this.reportingService.generatePurposeAccomplishmentRaport(orderId, userId)
            .then(((file) => {
                const fileURL = URL.createObjectURL(file);
                const a = document.createElement('a');
                document.body.appendChild(a);
                a.href = fileURL;
                a.download = filename + ".xlsx";
                a.click();
                this.isAvailable = true;
                this.showSuccess();
            }))
            .catch((error) => {
                console.log('consumer error ' + error);
                this.isAvailable = true;
                this.showError();
            });
    }
    generateRiskRegisterRaport(orderId: number, filename) {
        this.isAvailable = false;
        console.log(filename);
        this.reportingService.generateRiskRegisterRaport(orderId)
            .then(((file) => {
                const fileURL = URL.createObjectURL(file);
                const a = document.createElement('a');
                document.body.appendChild(a);
                a.href = fileURL;
                a.download = filename + ".xlsx";
                a.click();
                this.isAvailable = true;
                this.showSuccess();
            }))
            .catch((error) => {
                console.log('consumer error ' + error)
                this.isAvailable = true;
                this.showError();
            });
    }

    generateRiskReport(orderId: number, filename) {
        this.isAvailable = false;
        console.log(filename);
        console.log(orderId);
        this.reportingService.generateRiskReport(orderId)
            .then(((file) => {
                const fileURL = URL.createObjectURL(file);
                const a = document.createElement('a');
                document.body.appendChild(a);
                a.href = fileURL;
                a.download = filename + ".xlsx";
                a.click();
                this.isAvailable = true;
                this.showSuccess();
            }))
            .catch((error) => {
                console.log('consumer error ' + error)
                this.isAvailable = true;
                this.showError();
            });
    }

    generateRiskRegisterAdmin(orderId: number, filename) {
        this.isAvailable = false;
        console.log(filename);
        console.log(orderId);
        this.reportingService.generateRiskRegisterAdmin(orderId)
            .then(((file) => {
                const fileURL = URL.createObjectURL(file);
                const a = document.createElement('a');
                document.body.appendChild(a);
                a.href = fileURL;
                a.download = filename + ".xlsx";
                a.click();
                this.isAvailable = true;
                this.showSuccess();
            }))
            .catch((error) => {
                console.log('consumer error ' + error)
                this.isAvailable = true;
                this.showError();
            });
    }

    generateReportByProcesses() {
        console.log("Chosen process id: " + this.chosenProcessId);
        this.reportByProcessDialog = false;
    }

    generateRisksChangesReport(filename) {
        this.changesInRisks = false;
        this.isAvailable = false;

        this.reportingService.generateRisksChangesReport(this.orderId, this.chosenOrganisationStructureId)
            .then(((file) => {
                const fileURL = URL.createObjectURL(file);
                const a = document.createElement('a');
                document.body.appendChild(a);
                a.href = fileURL;
                a.download = filename + ".xlsx";
                a.click();
                this.isAvailable = true;
                this.showSuccess();
            }))
            .catch((error) => {
                console.log('consumer error ' + error)
                this.isAvailable = true;
                this.showSingleReportError();
            });
    }
    getAllParentedOrSupervisoredOrganisationStructures(type: String) {
        type === "risks" ? this.changesInRisks = true : console.log(type);
        type === "purposeAccomplishment" ? this.purposeAccomplishmentReportAdmin = true : console.log(type);

        console.log("getAllParentedOrSupervisoredOrganisationStructures function")
        this.reportingService.getAllParentedOrSupervisoredOrganisationStructures().subscribe(
            (res) => {this.organisationStructures = res.json},
            (error) => {console.log("consumer error: " + error)}
        );
    }

    generateRiskReportWord(orderId: number, filename) {
        this.isAvailable = false;
        console.log(filename);
        this.reportingService.generateRiskReportWord(orderId)
            .then(((file) => {
                const fileURL = URL.createObjectURL(file);
                const a = document.createElement('a');
                document.body.appendChild(a);
                a.href = fileURL;
                a.download = filename + ".docx";
                a.click();
                this.isAvailable = true;
                this.showSuccess();
            }))
            .catch((error) => {
                console.log('consumer error ' + error)
                this.isAvailable = true;
                this.showError();
            });
    }

    generatePurposeAccomplishmentRaportAdmin(filename) {

        this.purposeAccomplishmentReportAdmin = false;
        this.isAvailable = false;
        console.log(this.chosenOrganisationStructureId);
        this.reportingService.generatePurposeAccomplishmentRaportAdmin(this.orderId, this.chosenOrganisationStructureId)
            .then(((file) => {
                const fileURL = URL.createObjectURL(file);
                const a = document.createElement('a');
                document.body.appendChild(a);
                a.href = fileURL;
                a.download = filename + ".xlsx";
                a.click();
                this.isAvailable = true;
                this.showSuccess();
            }))
            .catch((error) => {
                console.log('consumer error ' + error)
                this.isAvailable = true;
                this.showSingleReportError();
            });
    }

}
