import {Component, OnDestroy, OnInit} from '@angular/core';
import {JhiAlertService, JhiParseLinks} from "ng-jhipster";
import {IncidentService} from "./incident.service";
import {ActivatedRoute} from "@angular/router";
import {Incident} from "./incident.model";
import {Orders} from "../orders";
import {Message} from "primeng/components/common/api";
import {MessageService} from "primeng/components/common/messageservice";
import {UserService} from "../../shared/user/user.service";
import {User} from "../../shared/user/user.model";
import {isNullOrUndefined} from "util";
import {SelectItem} from "primeng/primeng";
import {Principal} from "../../shared/auth/principal.service";

@Component({
    selector: 'incident-order',
    templateUrl: './incident-order.component.html',
})
export class IncidentOrderComponent implements OnInit, OnDestroy {
    orderId: any;
    incidents: Incident[];
    incidentsCopy: Incident[];
    user: User;
    users: User[];
    predicate: string;
    reverse: any;
    currentSearch: any;
    msgs: Message[] = [];
    types: SelectItem[];
    selectedType = 'Wszystkie zgłoszone incydenty';

    constructor(private activatedRoute: ActivatedRoute,
                private parseLinks: JhiParseLinks,
                private jhiAlertService: JhiAlertService,
                private incidentService: IncidentService,
                private messageService: MessageService,
                private userService: UserService,
                private principal: Principal) {
        this.types = [
            {label: 'Wszystkie', value: 'Wszystkie zgłoszone incydenty'},
            {label: 'Monitorowane przeze mnie', value: 'Tylko monitorowane przeze mnie incydenty'},
            {label: 'Niemonitorowane', value: 'Tylko niemonitorowane incydenty'}
        ];
    }

    ngOnInit(): void {
        this.userService.getAllUsersWithReducedData().subscribe((res) => {
            this.users = res.json;
        });
        this.principal.identity().then( (user: User) => {
                this.user = user;
            }
        ).catch( (error) => {
                console.error('consumer error ' + error);
            }
        );
        this.activatedRoute.params.subscribe(
            (param) => {
                this.orderId = param['orderId'];
                this.incidentService.getAllParentedOrSupervisoredCellsIncidents(this.orderId).subscribe(
                    (res) => {
                        this.incidents = res.json;
                        this.incidentsCopy = res.json
                    },
                    (error) => {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Błąd pobierania danych',
                            detail: 'Wystąpił błąd przy pobieraniu danych, skontaktuj się z administratorem. Błąd przy orderId: ' + this.orderId + ' /// ' + error
                        });
                        console.error('Wystąpił błąd przy pobieraniu danych, skontaktuj się z administratorem. Błąd przy orderId: ' + this.orderId + ' /// ' + error)
                    },
                    () => {
                    }
                );
            },
            (error) => {
                console.error("Nieprawidłowe przypisanie parametru - orderId")
            }
        );

    }

    ngOnDestroy(): void {
    }

    setSupervisor(incident: Incident) {
        this.incidentService.setSupervisor(incident).subscribe((res) => {
                incident.supervisedBy = res.supervisedBy;
                incident.statusOfIncident = res.statusOfIncident;
                this.messageService.add({
                    severity: 'success',
                    summary: 'Nadzorowanie incydentu',
                    detail: 'Pomyślnie przypisano nadzorowanie incydentu ' + res.id + ', ' + this.transform(res.description)
                });
            },
            (error) => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Błąd',
                    detail: 'Wystąpił błąd przy przypisania nadzorowania do incydentu. ' + error
                });
                console.error('Wystąpił błąd przy przypisania nadzorowania do incydentu. ' + error)
            });
    }

    transform(description: string): string {
        return description.length > 10 ? description.substring(0, 10) + "..." : description;
    }

    getReportingUserData(i: number): string {
        if (isNullOrUndefined(i)) {
            return '';
        } else {
            const user = this.users.find((x: User) => x.id === i);
            return isNullOrUndefined(user) ? 'Brak danych' : user.firstName + ' ' + user.lastName;
        }
    }

    getSupervisingUserData(i: number): string {
        if (isNullOrUndefined(i)) {
            return '';
        } else {
            const user = this.users.find((x: User) => x.id === i);
            return isNullOrUndefined(user) ? 'Brak danych' : ' przez ' + user.firstName + ' ' + user.lastName;
        }
    }

    changeTab(e) {
        this.incidents = [];
        this.incidents = this.incidentsCopy;
        if (e.index === 1) {
            this.incidents = this.incidents.filter((x: Incident) => x.supervisedBy === this.user.id);
        } else if (e.index === 2) {
            this.incidents = this.incidents.filter((x: Incident) => x.statusOfIncident === 'REPORTED');
        }
    }
    showWarn() {
        this.msgs = [];
        this.msgs.push({severity: 'warn', summary: 'Uwaga!', detail: 'Funkcjonalność nie została jeszcze przygotowana'});
    }
    sendEmail(i: Incident) {
        location.href = "mailto:" + i.user.email + "&subject=Monitorowanie incydentu o id: " + i.id + " zgłoszonego przez: " + i.user.firstName + ' ' + i.user.lastName;
    }
}
