import {Component, OnDestroy, OnInit, Injectable} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';
import {Message} from 'primeng/components/common/api';
import { ConfirmationService } from 'primeng/components/common/confirmationservice';

import {ResponseWrapper} from '../../shared';
import {GlossaryOfPurposesService} from '../glossary-of-purposes';
import {OrdersService} from './orders.service';
import {Orders} from './models/orders.model';
import {GlossaryOfPurposes} from '../glossary-of-purposes/glossary-of-purposes.model';
import 'rxjs/add/operator/takeWhile';
import {OrganisationStructureService} from '../organisation-structure/organisation-structure.service';
import {MeasuresDataClass} from './models/measures-data.model';
import {CommercialRisksDataClass} from './models/commercial-risks-data.model';
import {SendDataClass} from './models/send-data.model';
import {RisksDataClass} from './models/risks-data.model';
import {RisksFrontClass} from './models/risks-front.model';
import {MeasuresFrontClass} from './models/measures-front.model';
import {CheckboxesFromSelectDataModel} from './models/checkboxes-from-select-data.model';
import { NgbDatepickerI18n } from '@ng-bootstrap/ng-bootstrap';
import { DNI_I18N_PL } from './calendar.constants';
import {HighRiskDataModel} from './models/high-risk-data.model';

@Injectable()
export class PolishDatesInDatepicker extends NgbDatepickerI18n {

    constructor() {
        super();
    }

    getWeekdayShortName(weekday: number): string {
        return DNI_I18N_PL.dni[weekday - 1];
    }

    getMonthShortName(month: number): string {
        return DNI_I18N_PL.miesiace[month - 1];
    }

    getMonthFullName(month: number): string {
        return DNI_I18N_PL.pelneMiesiace[month - 1];
    }
}

@Component({
    selector: 'jhi-order-cele',
    templateUrl: './order-cele.component.html',
    styleUrls: ['./order-cele.component.css'],
    providers: [
        {provide: NgbDatepickerI18n, useClass: PolishDatesInDatepicker}
    ]
})
export class OrderCeleComponent implements OnInit, OnDestroy {
    glossaryOfPurposes: GlossaryOfPurposes[];
    purposesToCheck = [];
    purposesChecked = [];
    measures = [];
    risks = [];
    commercialRisks = [];
    setOfSentPurposesId: number;
    commercialRiskPurposesId: number;
    send = {
        'id': 0,
        'ids': [],
        'statusOfSending': '',
        'notation': '',
        'commercialRisksPurposesDTO': {
            'setOfSentPurposesId': 0,
            'id': 0,
            'filledCommercialRisksDTOS': Array<CommercialRisksDataClass>()
        },
        'measureUnitsPurposesDTOS': [
            {
                'setOfSentPurposesId': 0,
                'id': 0,
                'glossaryOfPurposesId': 0, // --------------чекнутая цель
                'filledMeasureUnitsDTOS': Array<MeasuresDataClass>(),
            }
        ],
        'risksPurposesDTOS': [
            {
                'setOfSentPurposesId': 0,
                'id': 0,
                'glossaryOfPurposesId': 0, // --------------чекнутая цель
                'filledRisksDTOS': Array<RisksDataClass>()
            }
        ]
    };
    _send = {
        'commercialRisksPurposesDTO': {
            'setOfSentPurposesId': 0,
            'id': 0,
            'filledCommercialRisksDTOS': Array<CommercialRisksDataClass>()
        },
        'measureUnitsPurposesDTOS': [
            {
                'glossaryOfPurposesId': 0, // --------------чекнутая цель
                'filledMeasureUnitsDTOS': Array<MeasuresDataClass>(),
            }
        ],
        'risksPurposesDTOS': [
            {
                'glossaryOfPurposesId': 0, // --------------чекнутая цель
                'filledRisksDTOS': Array<RisksDataClass>()
            }
        ]
    };

    formIsSaving = false;
    p_index: number;
    _p_index: number;
    _i: number;
    send_filledCommercialRisks = {};
    send_risksPurposesDTOS = {};
    order_id: number;
    purposesAreChecked = false;
    purposesBecomePlans = false;
    plansAreSendAndUnchecked = false;
    statusOfSending = '';
    setOfSentPurposes: any;
    order: Orders;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    checkboxesProbabilitiesAll: Array<CheckboxesFromSelectDataModel>;
    checkboxesToDisplay: Array<CheckboxesFromSelectDataModel>;
    checkboxesChecked: Array<number>;
    checkboxesPowerOfInfluenceAll: Array<CheckboxesFromSelectDataModel>;
    alive = true;
    selectM_baseValue_values = [
        {value: undefined, display: ''},
        {value: 1, display: ' 1 '},
        {value: 2, display: ' 2 '},
        {value: 3, display: ' 3 '},
        {value: 4, display: ' 4 '},
        {value: 5, display: ' 5 '},
    ];
    selectM_actualValue_values = [
        {value: undefined, display: ''},
        {value: 1, display: ' 1 '},
        {value: 2, display: ' 2 '},
        {value: 3, display: ' 3 '},
        {value: 4, display: ' 4 '},
        {value: 5, display: ' 5 '},
    ];
    selectM_finalValue_values = [
        {value: undefined, display: ''},
        {value: 1, display: ' 1 '},
        {value: 2, display: ' 2 '},
        {value: 3, display: ' 3 '},
        {value: 4, display: ' 4 '},
        {value: 5, display: ' 5 '},
    ];
    selectM_purposeAccomplished_active = '';
    selectM_purposeAccomplished_values = [
        {value: undefined, display: ''},
        {value: '_1_25', display: ' 1-25'},
        {value: '_26_50', display: '26-50'},
        {value: '_51_99', display: '51-99'},
        {value: '_100', display: '100'}
    ];
    selectR_strengthOfProbability_active = '';
    selectR_strengthOfProbability_values = [
        {value: undefined, display: ''},
        {value: 1, display: ' 1 '},
        {value: 2, display: ' 2 '},
        {value: 3, display: ' 3 '},
        {value: 4, display: ' 4 '},
        {value: 5, display: ' 5 '},
    ];
    selectR_strengthOfInfluence_active = '';
    selectR_strengthOfInfluence_values = [
        {value: undefined, display: ''},
        {value: 1, display: ' 1 '},
        {value: 2, display: ' 2 '},
        {value: 3, display: ' 3 '},
        {value: 4, display: ' 4 '},
        {value: 5, display: ' 5 '},
    ];
    selectCR_probability_active = '';
    selectCR_probability_values = [
        {value: undefined, display: ''},
        {value: 1, display: '1'},
        {value: 2, display: '2'},
        {value: 3, display: '3'},
        {value: 4, display: '4'},
        {value: 5, display: '5'},
    ];
    // {value: 1, display: 'Never'},
    // {value: 2, display: 'One time a year'},
    // {value: 3, display: 'One time per month'},
    // {value: 4, display: 'One time per week'},
    // {value: 5, display: 'Very often'},
    selectCR_strengthOfProbability_active = '';
    selectCR_strengthOfProbability_values = [
        {value: undefined, display: ''},
        {value: 1, display: ' 1 '},
        {value: 2, display: ' 2 '},
        {value: 3, display: ' 3 '},
        {value: 4, display: ' 4 '},
        {value: 5, display: ' 5 '},
    ];
    selectCR_strengthOfInfluence_active = '';
    selectCR_strengthOfInfluence_values = [
        {value: undefined, display: ''},
        {value: 1, display: ' 1 '},
        {value: 2, display: ' 2 '},
        {value: 3, display: ' 3 '},
        {value: 4, display: ' 4 '},
        {value: 5, display: ' 5 '},
    ];
    selectCR_influence_active = '';
    selectCR_influence_values = [
        {value: undefined, display: ''},
        {value: 1, display: '1'},
        {value: 2, display: '2'},
        {value: 3, display: '3'},
        {value: 4, display: '4'},
        {value: 5, display: '5'},
    ];
    selectCR_reactionOnRisks_active = '';
    selectCR_reactionOnRisks_values = [
        {value: undefined, display: ''},
        {value: 'ACCEPTATION_RISK', display: 'Akceptacja ryzyka'},
        {value: 'SHARE_RISK', display: 'Dzielenie się ryzykiem'},
        {value: 'AVOID_RISK', display: 'Unikanie ryzyka'},
        {value: 'RESTRICT_RISK', display: 'Ograniczenie ryzyka'},
        {value: 'REINFORCEMENT', display: 'Wzmocnienie'},
        {value: 'USAGE', display: 'Użycie'}
    ];
    popupCheckboxesActiveType: string;
    popupCheckboxesActiveValue: number;
    validationError = '';
    minProbability: number;
    maxProbability: number;
    minPowerOfInfluence: number;
    maxPowerOfInfluence: number;
    goodRisk_min: number;
    goodRisk_max: number;
    niceRisk_min: number;
    niceRisk_max: number;
    normalRisk_min: number;
    normalRisk_max: number;
    badRisk_min: number;
    badRisk_max: number;
    badRiskReached_cr = false;
    badRiskReached_r = false;
    riskToDisplay: RisksDataClass;
    comRiskToDisplay: CommercialRisksDataClass;
    temp_highRisk = Array<any>(0);
    highRisk_popupTitle = '';
    highRisk_popupPurpose = '';

    constructor(private eventManager: JhiEventManager,
                private ordersService: OrdersService,
                private glossaryOfPurposesService: GlossaryOfPurposesService,
                private organisationStructureService: OrganisationStructureService,
                private jhiAlertService: JhiAlertService,
                private route: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.loadOrder(params['id']);
            this.order_id = params['id'];

            this.loadCheckedOrders(params['id']);
        });
        this.registerChangeInOrderss();
        // this.glossaryOfPurposesService.query()

        // this.createEmptyCommercialRisks();
        this.setMinProbability();
        this.setMaxProbability();
        this.setMinPowerOfInfluence();
        this.setMaxPowerOfInfluence();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
        this.alive = false;
    }

    loadAll() {
        this.ordersService.getListOfPurposes(+this.order_id)
            .takeWhile(() => this.alive)
            .subscribe(
                (res: any) => {
                    const _glossaryOfPurposes = res;
                    if (this.purposesChecked.length) {
                        const temp = [];
                        this.purposesChecked.forEach((checkedId) => {
                            _glossaryOfPurposes.forEach((purpose) => {
                                if (+purpose.id === checkedId) {temp.push(purpose); }
                            });
                        });
                        this.glossaryOfPurposes = temp;
                    } else {this.glossaryOfPurposes = res; }
                    console.log(this.glossaryOfPurposes);
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    loadOrder(id: number) {
        this.ordersService.find(id)
            .takeWhile(() => this.alive)
            .subscribe((order: Orders) => {
                this.order = order;
                console.log(this.order);
            });
    }

    createEmptyCommercialRisks() {
        // check if data was saved and came from server:
        this.organisationStructureService.getListOfCommercialRisks()
            .takeWhile(() => this.alive)
            .subscribe(
                (res: any) => {
                    this.commercialRisks = res;
                    console.log(this.commercialRisks);

                    this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS = [];
                    this.commercialRisks.forEach((value) => {
                        (<SendDataClass>this.send).commercialRisksPurposesDTO.filledCommercialRisksDTOS
                            .push(new CommercialRisksDataClass(this.commercialRiskPurposesId,
                                undefined,
                                undefined,
                                undefined,
                                undefined,
                                {id: value.id, name: value.name},
                                undefined,
                                [],
                                undefined,
                                [],
                                false,
                                undefined,
                                undefined,
                                new HighRiskDataModel(undefined, undefined, undefined, undefined,
                                    undefined, undefined, undefined, undefined,
                                    undefined, undefined, undefined, undefined, undefined)));
                    });
                    this._send.commercialRisksPurposesDTO.filledCommercialRisksDTOS = [];
                    this.commercialRisks.forEach((value) => {
                        (this._send).commercialRisksPurposesDTO.filledCommercialRisksDTOS
                            .push(new CommercialRisksDataClass(this.commercialRiskPurposesId,
                                undefined,
                                undefined,
                                undefined,
                                undefined,
                                {id: value.id, name: value.name},
                                undefined,
                                [],
                                undefined,
                                [],
                                false,
                                undefined,
                                undefined,
                                new HighRiskDataModel(undefined, undefined, undefined, undefined,
                                    undefined, undefined, undefined, undefined,
                                    undefined, undefined, undefined, undefined, undefined)));
                    });
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    loadCheckedOrders(id: number) {
        this.formIsSaving = true;
        this.ordersService.getListOfCheckedPurposesForUser(id)
            .takeWhile(() => this.alive)
            .subscribe(
                (res: any) => {
                    this.formIsSaving = false;
                    console.log('---FROM SERVER: --');
                    console.log(res);
                    // this.statusOfSending = (res.statusOfSending === 'CONFIRMED_PURPOSES') ? 'UNCHECKED_PLAN' : res.statusOfSending;
                    this.statusOfSending = res.statusOfSending;
                    // console.log(this.statusOfSending);
                    this.loadAll();
                    if (res.id) {
                        this.commercialRiskPurposesId = (res.commercialRisksPurposesDTO && res.commercialRisksPurposesDTO.id) ? res.commercialRisksPurposesDTO.id : 0;
                        this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS.forEach((obj) => {
                            obj.commercialRiskPurposesId = (res.commercialRisksPurposesDTO && res.commercialRisksPurposesDTO.id) ? res.commercialRisksPurposesDTO.id : 0;
                        });
                        this.setOfSentPurposesId = res.id;
                        this.setOfSentPurposes = res;
                        this.purposesAreChecked = true;
                        this.purposesChecked = res.ids;
                        console.log(this.purposesChecked);
                        if (this.setOfSentPurposes.statusOfSending === 'REJECTED_PURPOSES') {
                            this.purposesAreChecked = false;
                            this.purposesToCheck = this.purposesChecked;
                            console.log(this.purposesToCheck);
                        } else if (this.setOfSentPurposes.statusOfSending === 'CONFIRMED_PLAN'
                            || this.setOfSentPurposes.statusOfSending === 'CONFIRMED_PURPOSES'
                            || this.setOfSentPurposes.statusOfSending === 'CONFIRMED_PLAN'
                            || this.setOfSentPurposes.statusOfSending === 'UNCHECKED_PLAN'
                            || this.setOfSentPurposes.statusOfSending === 'REJECTED_PLAN'
                            || this.setOfSentPurposes.statusOfSending === 'REJECTED_PLAN') {
                            if (res.measureUnitsPurposesDTOS.length && res.measureUnitsPurposesDTOS && res.measureUnitsPurposesDTOS['0'].glossaryOfPurposesId) {
                                res.measureUnitsPurposesDTOS.sort(function(first, second) {
                                    return first.glossaryOfPurposesId - second.glossaryOfPurposesId;
                                });
                            }
                            if (res.risksPurposesDTOS.length && res.risksPurposesDTOS && res.risksPurposesDTOS['0'].glossaryOfPurposesId) {
                                res.risksPurposesDTOS.sort(function(first, second) {
                                    return first.glossaryOfPurposesId - second.glossaryOfPurposesId;
                                });
                            }
                            this.purposesBecomePlans = true;
                            if (this.setOfSentPurposes.statusOfSending === 'UNCHECKED_PLAN') {
                                this.plansAreSendAndUnchecked = true;
                                console.log('DISABLED ' + this.plansAreSendAndUnchecked);
                            }
                            this.createSendJSON(res);
                            this.getListOfPowerOfInfluenceForPopup();
                            this.getListOfProbabilitiesForPopup();
                            this.getPercentagesOfCalculatedValuesFunction();
                        }
                    }
                },
                (res: ResponseWrapper) => {
                    this.formIsSaving = false;
                    this.onError(res.json);
                }
            );
    }

    getListOfPowerOfInfluenceForPopup() {
        this.glossaryOfPurposesService.getListOfPowerOfInfluence()
            .takeWhile(() => this.alive)
            .subscribe(
                (_res: any) => {
                    console.log(_res);
                    this.checkboxesPowerOfInfluenceAll = _res;
                },
                (_res: ResponseWrapper) => this.onError(_res.json)
            );
    }

    getListOfProbabilitiesForPopup() {
        this.glossaryOfPurposesService.getListOfProbabilities()
            .takeWhile(() => this.alive)
            .subscribe(
                (_res: any) => {
                    console.log(_res);
                    this.checkboxesProbabilitiesAll = _res;
                },
                (_res: ResponseWrapper) => this.onError(_res.json)
            );
    }

    createSendJSON(res: any) {
        this.send['id'] = res.id;
        this.send['ids'] = (res.ids && res.ids.length > 0) ? res.ids : undefined;
        this.send['statusOfSending'] = ((res.statusOfSending === 'CONFIRMED_PURPOSES')
            || (res.statusOfSending === 'REJECTED_PLAN')
            || (res.statusOfSending === 'CONFIRMED_PLAN')) ? 'UNCHECKED_PLAN' : res.statusOfSending;
        this.send['notation'] = (res.notation) ? res.notation : '';
        // this.send['commercialRisksPurposesDTO'] = {'filledCommercialRisksDTOS': Array<CommercialRisksDataClass>()};
        this.send.commercialRisksPurposesDTO.setOfSentPurposesId = this.setOfSentPurposesId;
        this.send.commercialRisksPurposesDTO.id = res.commercialRisksPurposesDTO.id;
        this.send['measureUnitsPurposesDTOS'] = [];
        this.send['risksPurposesDTOS'] = [];
        this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS = [];

        this._send.measureUnitsPurposesDTOS = [];
        this._send.risksPurposesDTOS = [];
        this._send.commercialRisksPurposesDTO.filledCommercialRisksDTOS = [];

        res.ids.forEach((value, index) => {
            Object.defineProperty((<SendDataClass>this.send).measureUnitsPurposesDTOS, value, {
                value: {
                    'setOfSentPurposesId': res.id,
                    'id': res.measureUnitsPurposesDTOS[index].id,
                    'glossaryOfPurposesId': value, // --------------чекнутая цель
                    'filledMeasureUnitsDTOS': []
                },
                writable: true,
                enumerable: true,
                configurable: true
            });
            Object.defineProperty((this._send).measureUnitsPurposesDTOS, value, {
                value: {
                    'glossaryOfPurposesId': value, // --------------чекнутая цель
                    'filledMeasureUnitsDTOS': []
                },
                writable: true,
                enumerable: true,
                configurable: true
            });
        });
        this.send.measureUnitsPurposesDTOS = (this.send).measureUnitsPurposesDTOS.filter((value) => Object.keys(value).length !== 0);
        this._send.measureUnitsPurposesDTOS = (this._send).measureUnitsPurposesDTOS.filter((value) => Object.keys(value).length !== 0);

        res.ids.forEach((value, index) => {
            Object.defineProperty((<SendDataClass>this.send).risksPurposesDTOS, value, {
                value: {
                    'setOfSentPurposesId': res.id,
                    'id': res.risksPurposesDTOS[index].id,
                    'glossaryOfPurposesId': value, // --------------чекнутая цель
                    'filledRisksDTOS': []
                },
                writable: true,
                enumerable: true,
                configurable: true
            });
            Object.defineProperty((this._send).risksPurposesDTOS, value, {
                value: {
                    'glossaryOfPurposesId': value, // --------------чекнутая цель
                    'filledRisksDTOS': []
                },
                writable: true,
                enumerable: true,
                configurable: true
            });
        });
        this.send.risksPurposesDTOS = this.send.risksPurposesDTOS.filter((value) => Object.keys(value).length !== 0);
        this._send.risksPurposesDTOS = this._send.risksPurposesDTOS.filter((value) => Object.keys(value).length !== 0);

        // check if data was saved and came from server:
        let measuresValuesToLoadFromServer = false;
        res.measureUnitsPurposesDTOS['0'].filledMeasureUnitsDTOS.forEach((value) => {
            if (value.glossaryOfMeasureUnitsDTO.id && value.creationDate) {
                measuresValuesToLoadFromServer = true;
            }
        });
        if (measuresValuesToLoadFromServer) {
            console.log('user created values for *MEASURES*, load it');
            res.measureUnitsPurposesDTOS.forEach((value, index) => {
                this.send.measureUnitsPurposesDTOS[index].filledMeasureUnitsDTOS = [];
                this.send.measureUnitsPurposesDTOS[index].filledMeasureUnitsDTOS = this.loadFreshestMEASURESValuesFromServerForOneCheckedPurpose(value);
                this._send.measureUnitsPurposesDTOS[index].filledMeasureUnitsDTOS = this.send.measureUnitsPurposesDTOS[index].filledMeasureUnitsDTOS;
            });
        } else { this.createEmptyMeasuresObjects(); }

        // check if data was saved and came from server:
        let risksValuesToLoadFromServer = false;
        res.risksPurposesDTOS['0'].filledRisksDTOS.forEach((value) => {
            if (value.glossaryOfRisksDTO.id && value.creationDate) {
                risksValuesToLoadFromServer = true;
            }
        });
        if (risksValuesToLoadFromServer) {
            console.log('user created values for *RISKS*, load it');
            this._loadFreshestRisksValuesFromServerForOneCheckedPurpose(res);
            // res.risksPurposesDTOS.forEach((value, index) => {
            //     this.send.risksPurposesDTOS[index].filledRisksDTOS = [];
            //     this.send.risksPurposesDTOS[index].filledRisksDTOS = this.loadFreshestRisksValuesFromServerForOneCheckedPurpose(value);
            //     this._send.risksPurposesDTOS[index].filledRisksDTOS = this.send.risksPurposesDTOS[index].filledRisksDTOS;
            // });
        } else { this.createEmptyRisksObjects(); }

        // check if data was saved and came from server:
        let commercialRisksValuesToLoadFromServer = false;
        res.commercialRisksPurposesDTO.filledCommercialRisksDTOS.forEach((value) => {
            if (value.creationDate) {
                commercialRisksValuesToLoadFromServer = true;
            }
        });
        if (res.statusOfSending === 'CONFIRMED_PLAN') {
            commercialRisksValuesToLoadFromServer = true;
        }
        if (commercialRisksValuesToLoadFromServer) {
            console.log('user created values for *CommercialRisks*, load it');
            this.loadCommercialRisksValuesFromServer(res);
        } else {
            this.createEmptyCommercialRisks();
        }

        // setTimeout(() => {
        //     this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS.forEach((value, i) => {
        //         this.validateSelectWithPopup('cr_probabilities', undefined, i);
        //         this.validateSelectWithPopup('cr_powerOfInfluence', undefined, i);
        //     });
        // }, 1000);

        this.formIsSaving = false;
        console.log(this.send);
        console.log(this._send);
    }

    createEmptyMeasuresObjects() {
        console.log('createEmptyMeasuresObjects');
        this.send.measureUnitsPurposesDTOS.forEach((purpose_id, index) => {
            let measures = [];
            this.glossaryOfPurposesService.getListOfMeasures(purpose_id.glossaryOfPurposesId)
                .takeWhile(() => this.alive)
                .subscribe(
                    (_res: any) => {
                        // console.log('----------Measures----------');
                        measures = _res;
                        console.log(measures);

                        // create empty structure
                        measures.forEach((measure) => {
                            purpose_id.filledMeasureUnitsDTOS
                                .push(new MeasuresDataClass(purpose_id.id, undefined, undefined, undefined, undefined,
                                    {id: measure.id, name: measure.name, unitsOfMeasurement: measure.unitsOfMeasurement}, false, undefined));
                        });

                        this._send.measureUnitsPurposesDTOS[index].filledMeasureUnitsDTOS = measures;
                    },
                    (_res: ResponseWrapper) => this.onError(_res.json)
                );
        });
    }

    createEmptyRisksObjects() {
        console.log('createEmptyRisksObjects');
        this.send.risksPurposesDTOS.forEach((purpose_id, index) => {
            let risks = [];
            this.glossaryOfPurposesService.getListOfRisks(purpose_id.glossaryOfPurposesId)
                .takeWhile(() => this.alive)
                .subscribe(
                    (_res: any) => {
                        // console.log('----------Risks----------');
                        risks = _res;
                        // console.log(risks);

                        // create empty structure
                        risks.forEach((risk) => {
                            purpose_id.filledRisksDTOS
                                .push(new RisksDataClass(purpose_id.id, undefined, undefined, undefined, undefined, {id: risk.id, name: risk.name},
                                    undefined, [], undefined, [], false, undefined, undefined,
                                    new HighRiskDataModel(undefined, undefined, undefined, undefined,
                                        undefined, undefined, undefined, undefined,
                                        undefined, undefined, undefined, undefined, undefined)));
                        });

                        this._send.risksPurposesDTOS[index].filledRisksDTOS = risks;
                    },
                    (_res: ResponseWrapper) => this.onError(_res.json)
                );
        });
    }

    public loadCommercialRisksValuesFromServer(res: any) {
        const resultArray = Array<CommercialRisksDataClass>(0);
        const ArrayOfObjects = res.commercialRisksPurposesDTO.filledCommercialRisksDTOS;
        const lastChanges_dateMs = Math.max.apply(Math, ArrayOfObjects.map(function(o) {
            return +(new Date(o.creationDate));
        }));
        const freshestArrayOfObjects = (ArrayOfObjects.filter(function(obj) {
            return +(new Date(obj.creationDate)) === lastChanges_dateMs;
        }));
        // console.log(freshestArrayOfObjects);
        if (freshestArrayOfObjects.length) {
            this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS = [];
            freshestArrayOfObjects.forEach((freshestObj) => {
                this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS
                    .push(new CommercialRisksDataClass(freshestObj.commercialRiskPurposesId,
                        freshestObj.powerOfInfluence,
                        freshestObj.probability,
                        freshestObj.reactionOnRisks,
                        freshestObj.notationConcernRisk,
                        freshestObj.glossaryOfCommercialRisksDTO,
                        freshestObj.strengthOfControlFunctionProbability,
                        freshestObj.probabilities,
                        freshestObj.strengthOfControlFunctionPowerOfInfluence,
                        freshestObj.powerOfInfluences,
                        false,
                        freshestObj.responsiblePerson,
                        freshestObj.stateForDay,
                        new HighRiskDataModel((freshestObj.highRiskDTO && freshestObj.highRiskDTO.analyze) ? (freshestObj.highRiskDTO.analyze) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.commentToHighRiskProcedure) ? (freshestObj.highRiskDTO.commentToHighRiskProcedure) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.costOfListedPossibilities) ? (freshestObj.highRiskDTO.costOfListedPossibilities) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.decisionForRiskId) ? (freshestObj.highRiskDTO.decisionForRiskId) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.possibilitiesToImproveRisks) ? (freshestObj.highRiskDTO.possibilitiesToImproveRisks) : undefined,
                            (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.postponePurposeAccomplishment === true
                                                                                || freshestObj.highRiskDTO.postponePurposeAccomplishment === false)) ? (freshestObj.highRiskDTO.postponePurposeAccomplishment) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.powerOfInfluenceToReach) ? (freshestObj.highRiskDTO.powerOfInfluenceToReach) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.probabilityToReach) ? (freshestObj.highRiskDTO.probabilityToReach) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.projectedTermDeployFinish) ? (freshestObj.highRiskDTO.projectedTermDeployFinish) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.projectedTermDeployStart) ? (freshestObj.highRiskDTO.projectedTermDeployStart) : undefined,
                            (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.rejectToAccomplishPurpose === true
                                                                            || freshestObj.highRiskDTO.rejectToAccomplishPurpose === false)) ? (freshestObj.highRiskDTO.rejectToAccomplishPurpose) : undefined,
                            (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment === true
                                                                                    || freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment === false)) ? (freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.substantiationForAnalyze) ? (freshestObj.highRiskDTO.substantiationForAnalyze) : undefined)));
                this._send.commercialRisksPurposesDTO.filledCommercialRisksDTOS
                    .push(new CommercialRisksDataClass(freshestObj.commercialRiskPurposesId,
                        freshestObj.powerOfInfluence,
                        freshestObj.probability,
                        freshestObj.reactionOnRisks,
                        freshestObj.notationConcernRisk,
                        freshestObj.glossaryOfCommercialRisksDTO,
                        freshestObj.strengthOfControlFunctionProbability,
                        freshestObj.probabilities,
                        freshestObj.strengthOfControlFunctionPowerOfInfluence,
                        freshestObj.powerOfInfluences,
                        false,
                        freshestObj.responsiblePerson,
                        {
                            year: (freshestObj.stateForDay && freshestObj.stateForDay.indexOf('-')) ? +(freshestObj.stateForDay.split('-')[0]) : '',
                            month: (freshestObj.stateForDay && freshestObj.stateForDay.indexOf('-')) ? +(freshestObj.stateForDay.split('-')[1]) : '',
                            day: (freshestObj.stateForDay && freshestObj.stateForDay.indexOf('-')) ? +(freshestObj.stateForDay.split('-')[2]) : ''
                        },
                        new HighRiskDataModel((freshestObj.highRiskDTO && freshestObj.highRiskDTO.analyze) ? (freshestObj.highRiskDTO.analyze) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.commentToHighRiskProcedure) ? (freshestObj.highRiskDTO.commentToHighRiskProcedure) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.costOfListedPossibilities) ? (freshestObj.highRiskDTO.costOfListedPossibilities) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.decisionForRiskId) ? (freshestObj.highRiskDTO.decisionForRiskId) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.possibilitiesToImproveRisks) ? (freshestObj.highRiskDTO.possibilitiesToImproveRisks) : undefined,
                            (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.postponePurposeAccomplishment === true
                                                                                || freshestObj.highRiskDTO.postponePurposeAccomplishment === false)) ? (freshestObj.highRiskDTO.postponePurposeAccomplishment) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.powerOfInfluenceToReach) ? (freshestObj.highRiskDTO.powerOfInfluenceToReach) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.probabilityToReach) ? (freshestObj.highRiskDTO.probabilityToReach) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.projectedTermDeployFinish) ? (freshestObj.highRiskDTO.projectedTermDeployFinish) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.projectedTermDeployStart) ? (freshestObj.highRiskDTO.projectedTermDeployStart) : undefined,
                            (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.rejectToAccomplishPurpose === true
                                                                            || freshestObj.highRiskDTO.rejectToAccomplishPurpose === false)) ? (freshestObj.highRiskDTO.rejectToAccomplishPurpose) : undefined,
                            (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment === true
                                                                                    || freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment === false)) ? (freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.substantiationForAnalyze) ? (freshestObj.highRiskDTO.substantiationForAnalyze) : undefined)));
            });
        }
    }

    public loadFreshestMEASURESValuesFromServerForOneCheckedPurpose(measureUnitsPurposesDTOS: any): Array<MeasuresDataClass> {
        let resultArray = Array<MeasuresDataClass>(0);
        const measuresId = [];
        measureUnitsPurposesDTOS.filledMeasureUnitsDTOS.forEach((value) => {
            let included = false;
            measuresId.forEach((v) => {
                if (v && (+v === +value.glossaryOfMeasureUnitsDTO.id)) {
                    included = true;
                }
            });
            if (!included) {
                measuresId.push(value.glossaryOfMeasureUnitsDTO.id);
            }
        });
        // console.log(measuresId);

        let lastChanges_dateMs: number;
        measuresId.forEach((m_id) => {
            const measureHistoriesArray = (measureUnitsPurposesDTOS.filledMeasureUnitsDTOS).filter((m) => {
                return m.glossaryOfMeasureUnitsDTO.id === m_id;
            });
            // console.log(measureHistoriesArray);
            lastChanges_dateMs = Math.max.apply(Math, measureHistoriesArray.map(function(o) {
                return +(new Date(o.creationDate));
            }));
            const freshestObj = ((measureHistoriesArray.filter(function(obj) {
                return +(new Date(obj.creationDate)) === lastChanges_dateMs;
            })))['0'];
            // console.log(freshestObj);

            resultArray.push(new MeasuresDataClass(+freshestObj.measureUnitsPurposesId,
                (+freshestObj.baseValue === 0) ? undefined : +freshestObj.baseValue,
                (+freshestObj.actualValue === 0) ? undefined : +freshestObj.actualValue,
                (+freshestObj.finalValue === 0) ? undefined : +freshestObj.finalValue,
                freshestObj.purposeAccomplishmentStatus,
                {id: freshestObj.glossaryOfMeasureUnitsDTO.id,
                    name: freshestObj.glossaryOfMeasureUnitsDTO.name,
                    unitsOfMeasurement: freshestObj.glossaryOfMeasureUnitsDTO.unitsOfMeasurement},
                freshestObj.saved,
                (+freshestObj.costOfPurposeRealisation === 0) ? undefined : +freshestObj.costOfPurposeRealisation));
        });

        // console.log(resultArray.length);
        // console.log(resultArray);
        return resultArray;
    }

    public loadFreshestRisksValuesFromServerForOneCheckedPurpose(risksPurposesDTOS: any): Array<RisksDataClass> {
        let resultArray = Array<RisksDataClass>(0);
        const risksId = [];
        risksPurposesDTOS.filledRisksDTOS.forEach((value) => {
            let included = false;
            risksId.forEach((v) => {
                if (v && (+v === +value.glossaryOfRisksDTO.id)) {
                    included = true;
                }
            });
            if (!included) {
                risksId.push(value.glossaryOfRisksDTO.id);
            }
        });
        // console.log(risksId);

        let lastChanges_dateMs: number;
        risksId.forEach((r_id) => {
            const riskHistoriesArray = (risksPurposesDTOS.filledRisksDTOS).filter((r) => {
                return r.glossaryOfRisksDTO.id === r_id;
            });
            // console.log(riskHistoriesArray);
            lastChanges_dateMs = Math.max.apply(Math, riskHistoriesArray.map(function(o) {
                return +(new Date(o.creationDate));
            }));
            const freshestObj = ((riskHistoriesArray.filter(function(obj) {
                return +(new Date(obj.creationDate)) === lastChanges_dateMs;
            })))['0'];
            // console.log(freshestObj);
            resultArray.push(new RisksDataClass(+freshestObj.risksPurposesId,
                (+freshestObj.powerOfInfluence === 0) ? undefined : +freshestObj.powerOfInfluence,
                (+freshestObj.probability === 0) ? undefined : +freshestObj.probability,
                freshestObj.reactionOnRisks,
                freshestObj.notationConcernRisk,
                {id: freshestObj.glossaryOfRisksDTO.id, name: freshestObj.glossaryOfRisksDTO.name},
                (+freshestObj.strengthOfControlFunctionProbability === 0) ? undefined : +freshestObj.strengthOfControlFunctionProbability,
                freshestObj.probabilities,
                (+freshestObj.strengthOfControlFunctionPowerOfInfluence === 0) ? undefined : +freshestObj.strengthOfControlFunctionPowerOfInfluence,
                freshestObj.powerOfInfluences,
                freshestObj.saved,
                freshestObj.responsiblePerson,
                {
                    year: (freshestObj.stateForDay && freshestObj.stateForDay.indexOf('-')) ? +(freshestObj.stateForDay.split('-')[0]) : '',
                    month: (freshestObj.stateForDay && freshestObj.stateForDay.indexOf('-')) ? +(freshestObj.stateForDay.split('-')[1]) : '',
                    day: (freshestObj.stateForDay && freshestObj.stateForDay.indexOf('-')) ? +(freshestObj.stateForDay.split('-')[2]) : ''
                },
                new HighRiskDataModel((freshestObj.highRiskDTO && freshestObj.highRiskDTO.analyze) ? (freshestObj.highRiskDTO.analyze) : undefined,
                    (freshestObj.highRiskDTO && freshestObj.highRiskDTO.commentToHighRiskProcedure) ? (freshestObj.highRiskDTO.commentToHighRiskProcedure) : undefined,
                    (freshestObj.highRiskDTO && freshestObj.highRiskDTO.costOfListedPossibilities) ? (freshestObj.highRiskDTO.costOfListedPossibilities) : undefined,
                    (freshestObj.highRiskDTO && freshestObj.highRiskDTO.decisionForRiskId) ? (freshestObj.highRiskDTO.decisionForRiskId) : undefined,
                    (freshestObj.highRiskDTO && freshestObj.highRiskDTO.possibilitiesToImproveRisks) ? (freshestObj.highRiskDTO.possibilitiesToImproveRisks) : undefined,
                    (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.postponePurposeAccomplishment === true
                                                                        || freshestObj.highRiskDTO.postponePurposeAccomplishment === false))
                        ? (freshestObj.highRiskDTO.postponePurposeAccomplishment) : undefined,
                    (freshestObj.highRiskDTO && freshestObj.highRiskDTO.powerOfInfluenceToReach) ? (freshestObj.highRiskDTO.powerOfInfluenceToReach) : undefined,
                    (freshestObj.highRiskDTO && freshestObj.highRiskDTO.probabilityToReach) ? (freshestObj.highRiskDTO.probabilityToReach) : undefined,
                    (freshestObj.highRiskDTO && freshestObj.highRiskDTO.projectedTermDeployFinish) ? (freshestObj.highRiskDTO.projectedTermDeployFinish) : undefined,
                    (freshestObj.highRiskDTO && freshestObj.highRiskDTO.projectedTermDeployStart) ? (freshestObj.highRiskDTO.projectedTermDeployStart) : undefined,
                    (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.rejectToAccomplishPurpose === true
                                                                    || freshestObj.highRiskDTO.rejectToAccomplishPurpose === false))
                        ? (freshestObj.highRiskDTO.rejectToAccomplishPurpose) : undefined,
                    (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment === true
                                                                            || freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment === false))
                        ? (freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment) : undefined,
                    (freshestObj.highRiskDTO && freshestObj.highRiskDTO.substantiationForAnalyze) ? (freshestObj.highRiskDTO.substantiationForAnalyze) : undefined)));
        });
        // console.log(resultArray);
        return resultArray;
    }

    public _loadFreshestRisksValuesFromServerForOneCheckedPurpose(res: any) {
        // console.log('----- s:');
        res.risksPurposesDTOS.forEach((riskPurposesDTOS, index, arr1) => {
            // console.log('checked ' + arr1.length + ' purposes');
            this.send.risksPurposesDTOS[index].filledRisksDTOS = [];
            this._send.risksPurposesDTOS[index].filledRisksDTOS = [];
            let resultArray = Array<RisksDataClass>(0);
            const risksId = [];
            riskPurposesDTOS.filledRisksDTOS.forEach((value) => {
                let included = false;
                risksId.forEach((v) => {
                    if (v && (+v === +value.glossaryOfRisksDTO.id)) {
                        included = true;
                    }
                });
                if (!included) {
                    risksId.push(value.glossaryOfRisksDTO.id);
                }
            });
            // console.log(risksId);

            let lastChanges_dateMs: number;
            risksId.forEach((r_id) => {
                const riskHistoriesArray = (riskPurposesDTOS.filledRisksDTOS).filter((r) => {
                    return r.glossaryOfRisksDTO.id === r_id;
                });
                // console.log(riskHistoriesArray);
                lastChanges_dateMs = Math.max.apply(Math, riskHistoriesArray.map(function(o) {
                    return +(new Date(o.creationDate));
                }));
                const freshestObj = ((riskHistoriesArray.filter(function(obj) {
                    return +(new Date(obj.creationDate)) === lastChanges_dateMs;
                })))['0'];
                console.log('freshestObj');
                console.log(freshestObj);

                this.send.risksPurposesDTOS[index].filledRisksDTOS
                    .push(new RisksDataClass(freshestObj.risksPurposesId,
                        (+freshestObj.powerOfInfluence === 0) ? undefined : +freshestObj.powerOfInfluence,
                        (+freshestObj.probability === 0) ? undefined : +freshestObj.probability,
                        freshestObj.reactionOnRisks,
                        freshestObj.notationConcernRisk,
                        {id: freshestObj.glossaryOfRisksDTO.id, name: freshestObj.glossaryOfRisksDTO.name},
                        (+freshestObj.strengthOfControlFunctionProbability === 0) ? undefined : +freshestObj.strengthOfControlFunctionProbability,
                        freshestObj.probabilities,
                        (+freshestObj.strengthOfControlFunctionPowerOfInfluence === 0) ? undefined : +freshestObj.strengthOfControlFunctionPowerOfInfluence,
                        freshestObj.powerOfInfluences,
                        false,
                        freshestObj.responsiblePerson,
                        (freshestObj.stateForDay) ? freshestObj.stateForDay : '',
                        new HighRiskDataModel((freshestObj.highRiskDTO && freshestObj.highRiskDTO.analyze) ? (freshestObj.highRiskDTO.analyze) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.commentToHighRiskProcedure) ? (freshestObj.highRiskDTO.commentToHighRiskProcedure) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.costOfListedPossibilities) ? (freshestObj.highRiskDTO.costOfListedPossibilities) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.decisionForRiskId) ? (freshestObj.highRiskDTO.decisionForRiskId) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.possibilitiesToImproveRisks) ? (freshestObj.highRiskDTO.possibilitiesToImproveRisks) : undefined,
                            (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.postponePurposeAccomplishment === true
                                                                                || freshestObj.highRiskDTO.postponePurposeAccomplishment === false)) ? (freshestObj.highRiskDTO.postponePurposeAccomplishment) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.powerOfInfluenceToReach) ? (freshestObj.highRiskDTO.powerOfInfluenceToReach) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.probabilityToReach) ? (freshestObj.highRiskDTO.probabilityToReach) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.projectedTermDeployFinish) ? (freshestObj.highRiskDTO.projectedTermDeployFinish) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.projectedTermDeployStart) ? (freshestObj.highRiskDTO.projectedTermDeployStart) : undefined,
                            (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.rejectToAccomplishPurpose === true
                                                                            || freshestObj.highRiskDTO.rejectToAccomplishPurpose === false)) ? (freshestObj.highRiskDTO.rejectToAccomplishPurpose) : undefined,
                            (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment === true
                                                                                    || freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment === false)) ? (freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.substantiationForAnalyze) ? (freshestObj.highRiskDTO.substantiationForAnalyze) : undefined)));
                // console.log(index, freshestObj.stateForDay);

                this._send.risksPurposesDTOS[index].filledRisksDTOS
                    .push(new RisksDataClass(freshestObj.risksPurposesId,
                        (+freshestObj.powerOfInfluence === 0) ? undefined : +freshestObj.powerOfInfluence,
                        (+freshestObj.probability === 0) ? undefined : +freshestObj.probability,
                        freshestObj.reactionOnRisks,
                        freshestObj.notationConcernRisk,
                        {id: freshestObj.glossaryOfRisksDTO.id, name: freshestObj.glossaryOfRisksDTO.name},
                        (+freshestObj.strengthOfControlFunctionProbability === 0) ? undefined : +freshestObj.strengthOfControlFunctionProbability,
                        freshestObj.probabilities,
                        (+freshestObj.strengthOfControlFunctionPowerOfInfluence === 0) ? undefined : +freshestObj.strengthOfControlFunctionPowerOfInfluence,
                        freshestObj.powerOfInfluences,
                        false,
                        freshestObj.responsiblePerson,
                        {
                            year: (freshestObj.stateForDay && freshestObj.stateForDay.indexOf('-')) ? +(freshestObj.stateForDay.split('-')[0]) : '',
                            month: (freshestObj.stateForDay && freshestObj.stateForDay.indexOf('-')) ? +(freshestObj.stateForDay.split('-')[1]) : '',
                            day: (freshestObj.stateForDay && freshestObj.stateForDay.indexOf('-')) ? +(freshestObj.stateForDay.split('-')[2]) : ''
                        },
                        new HighRiskDataModel((freshestObj.highRiskDTO && freshestObj.highRiskDTO.analyze) ? (freshestObj.highRiskDTO.analyze) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.commentToHighRiskProcedure) ? (freshestObj.highRiskDTO.commentToHighRiskProcedure) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.costOfListedPossibilities) ? (freshestObj.highRiskDTO.costOfListedPossibilities) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.decisionForRiskId) ? (freshestObj.highRiskDTO.decisionForRiskId) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.possibilitiesToImproveRisks) ? (freshestObj.highRiskDTO.possibilitiesToImproveRisks) : undefined,
                            (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.postponePurposeAccomplishment === true
                                                                                || freshestObj.highRiskDTO.postponePurposeAccomplishment === false)) ? (freshestObj.highRiskDTO.postponePurposeAccomplishment) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.powerOfInfluenceToReach) ? (freshestObj.highRiskDTO.powerOfInfluenceToReach) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.probabilityToReach) ? (freshestObj.highRiskDTO.probabilityToReach) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.projectedTermDeployFinish) ? (freshestObj.highRiskDTO.projectedTermDeployFinish) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.projectedTermDeployStart) ? (freshestObj.highRiskDTO.projectedTermDeployStart) : undefined,
                            (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.rejectToAccomplishPurpose === true
                                                                            || freshestObj.highRiskDTO.rejectToAccomplishPurpose === false)) ? (freshestObj.highRiskDTO.rejectToAccomplishPurpose) : undefined,
                            (freshestObj.highRiskDTO && (freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment === true
                                                                                    || freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment === false)) ? (freshestObj.highRiskDTO.restrictRangeOfPurposeAccomplishment) : undefined,
                            (freshestObj.highRiskDTO && freshestObj.highRiskDTO.substantiationForAnalyze) ? (freshestObj.highRiskDTO.substantiationForAnalyze) : undefined)));

            });
            // console.log(resultArray);

        });
        // console.log('----- end:');
    }

    trackId(index: number, item: GlossaryOfPurposes) {
        return item.id;
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    registerChangeInOrderss() {
        this.eventSubscriber = this.eventManager.subscribe(
            'getListOfPurposes',
            (response) => this.loadOrder(this.order.id)
        );
    }

    public addPurpose(e: any) {
        const userInput = e.target;
        if (userInput.checked) {
            this.purposesToCheck.push(+userInput.id);
            this.purposesToCheck = this.purposesToCheck.filter((elem, index, self) => {
                return index === self.indexOf(elem);
            });
        }
        if (!userInput.checked) {
            this.purposesToCheck = this.purposesToCheck.filter((val) => val !== +userInput.id);
        }

        // if (this.drivingLicense.categories.length === 0) {
        //     this.categoryDrivingLicense_nullData = true;
        // } else {
        //     this.categoryDrivingLicense_nullData = false;
        // }
        console.log(this.purposesToCheck);
    }

    clearCheckboxes() {
        this.purposesToCheck = [];
        const checkedI: NodeListOf<Element> = window.document.querySelectorAll('input[type=checkbox]:checked');
        for (let i = 0; i < checkedI.length; i++) {
            (<HTMLInputElement>checkedI[i]).checked = false;
        }
    }

    public getPercentagesOfCalculatedValuesFunction() {
        this.ordersService.getPercentagesOfCalculatedValues()
            .takeWhile(() => this.alive)
            .subscribe(
                (res: any) => {
                    // console.log(res);
                    this.goodRisk_min = (res[0].min) ? (res[0].min) : 1;
                    this.goodRisk_max = (res[0].max) ? (res[0].max) : 19;
                    this.niceRisk_min = (res[1].min) ? (res[1].min) : 20;
                    this.niceRisk_max = (res[1].max) ? (res[1].max) : 39;
                    this.normalRisk_min = (res[2].min) ? (res[2].min) : 40;
                    this.normalRisk_max = (res[2].max) ? (res[2].max) : 59;
                    this.badRisk_min = (res[3].min) ? (res[3].min) : 60;
                    this.badRisk_max = (res[3].max) ? (res[3].max) : 100;
                },
                (res: ResponseWrapper) => {
                    this.onError(res.json);
                }
            );
    }

    public submitSaveCeleFunction() {
        console.log(this.purposesToCheck);
        console.log(this.purposesAreChecked);
        if (!this.purposesAreChecked && this.purposesToCheck.length) {
            this.formIsSaving = true;
            this.ordersService.createSetOfSentPurposes(this.order_id, this.purposesToCheck)
                .takeWhile(() => this.alive)
                .subscribe(
                    (res: any) => {
                        console.log(res.status);
                        // if (res.status) {
                        //     this.router.navigate(['/orders-user']);
                        // }
                        if (res.status === 200) {
                            this.loadCheckedOrders(this.order_id);
                        }
                    },
                    (res: ResponseWrapper) => {
                        this.formIsSaving = false;
                        this.onError(res.json);
                    }
                );
        } else {
            return false;
        }
    }

    public accordionToggleItemFunction(e: any, purpose_id: number) {
        if (this.purposesAreChecked && this.purposesBecomePlans) {
            const currentClass = e.currentTarget.className;
            const accordionItems: NodeListOf<Element> = window.document.querySelectorAll('tr.accordionItem');

            for (let i = 0; i < accordionItems.length; i++) {
                if (accordionItems[i].className === 'accordionItem close-item') {
                    continue;
                }
                accordionItems[i].className = 'accordionItem close-item';
            }

            const currentAccordionItem = e.currentTarget;
            // if ((currentClass === 'accordionItem close-item')) {return true; }
            if ((currentClass === 'accordionItem close-item')) {
                currentAccordionItem.classList.remove('close-item');
                currentAccordionItem.classList.add('open');
                if (e.currentTarget.nextElementSibling
                    && e.currentTarget.nextElementSibling.nextElementSibling
                    && e.currentTarget.nextElementSibling.nextElementSibling.className
                    && e.currentTarget.nextElementSibling.nextElementSibling.className === 'accordionItemContent') {
                    (<SendDataClass>this.send).measureUnitsPurposesDTOS.forEach((value, index) => {
                        if (value.glossaryOfPurposesId === purpose_id) {
                            this.p_index = index;
                            console.log('PPPPPPPPPPPPPPP', this.p_index);
                            // setTimeout(() => {
                            //     this.send.risksPurposesDTOS.forEach((risksPurpose) => {
                            //         risksPurpose.filledRisksDTOS.forEach((risk, i) => {
                            //             this.validateSelectWithPopup('r_probabilities', this.p_index, i);
                            //             this.validateSelectWithPopup('r_powerOfInfluence', this.p_index, i);
                            //         });
                            //     })
                            // }, 1000);
                        }
                    });
                    e.currentTarget.classList.add('bordered');
                    this.getMeasureRisksFunction(purpose_id);
                } else {
                    e.currentTarget.classList.remove('bordered');
                    // this.p_index = undefined;
                }
            } else {
                // currentAccordionItem.className = 'accordionItem close-item';
            }
        }
    }

    showMeasuresIndex(i: any) {
        // console.log(i);
    }

    showPurpIndex(i: any) {
        // console.log(i);
    }

    getMeasureRisksFunction(purpose_id: number) {
        if (this.purposesAreChecked) {
            // console.log(purpose_id);
            this.glossaryOfPurposesService.getListOfMeasures(purpose_id)
                .takeWhile(() => this.alive)
                .subscribe(
                    (res: any) => {
                        console.log('----------Measures----------');
                        this.measures = res;
                    },
                    (res: ResponseWrapper) => this.onError(res.json)
                );

            this.glossaryOfPurposesService.getListOfRisks(purpose_id)
                .takeWhile(() => this.alive)
                .subscribe(
                    (res: any) => {
                        console.log('----------Risks----------');
                        this.risks = res;
                    },
                    (res: ResponseWrapper) => this.onError(res.json)
                );
        }
    }

    public copyToSend_FilledCommercialRisks() {
        (<SendDataClass>this.send).commercialRisksPurposesDTO.filledCommercialRisksDTOS = [];
        for (const i in this.send_filledCommercialRisks) {
            (<SendDataClass>this.send).commercialRisksPurposesDTO.filledCommercialRisksDTOS.push(this.send_filledCommercialRisks[i]);
        }
        console.log(this.send);
    }

    public selectCRInfluenceChanged(id: number, value: number) {
        console.log('--------Influence ' + id + ': ' + value);
        this.send_filledCommercialRisks[id].powerOfInfluence = value;
        this.copyToSend_FilledCommercialRisks();
    }

    public selectCRProbabilityChanged(id: number, value: number) {
        console.log('--------CR probability  ' + id + ': ' + value);
        this.send_filledCommercialRisks[id].probability = value;
        this.copyToSend_FilledCommercialRisks();
    }

    public selectCRreactionChanged(id: number, value: string) {
        console.log('--------CR reaction ' + id + ': ' + value);
        this.send_filledCommercialRisks[id].reactionOnRisks = value;
        this.copyToSend_FilledCommercialRisks();
    }

    public deleteEmptyObjectsFromSendJSON1() {
        if (this.send.measureUnitsPurposesDTOS && this.send.measureUnitsPurposesDTOS.length > 0) {
            this.send.measureUnitsPurposesDTOS.forEach((value) => {
                const temporary = [];
                if (value.filledMeasureUnitsDTOS && value.filledMeasureUnitsDTOS.length > 0) {
                    value.filledMeasureUnitsDTOS.forEach((r, i, array) => {
                        if (r.baseValue && r.actualValue && r.finalValue && r.purposeAccomplishmentStatus && r.costOfPurposeRealisation) {
                            // console.log('deleted empty measure');
                            // array.splice(i, 1);
                            temporary.push(r);
                        }
                    });
                    value.filledMeasureUnitsDTOS = temporary;
                }
            });
        }
        if (this.send.risksPurposesDTOS && this.send.risksPurposesDTOS.length > 0) {
            this.send.risksPurposesDTOS.forEach((value) => {
                const temporary = [];
                if (value.filledRisksDTOS && value.filledRisksDTOS.length > 0) {
                    value.filledRisksDTOS.forEach((r, i, array) => {
                        if (r.powerOfInfluence && r.probability && r.reactionOnRisks && r.notationConcernRisk && r.responsiblePerson && r.stateForDay) {
                            // console.log('deleted empty Risk');
                            // array.splice(i, 1);
                            temporary.push(r);
                        }
                    });
                    value.filledRisksDTOS = temporary;
                }
            });
        }
        if (this.send.commercialRisksPurposesDTO && this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS.length > 0) {
            const temporary = [];
            this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS.forEach((r, i, array) => {
                if ((this.statusOfSending === 'CONFIRMED_PLAN') || (this.statusOfSending === 'REJECTED_PLAN')) {
                    delete r['id'];
                    delete r['creationDate'];
                    console.log('deleted id+creationDate from [filledCommercialRisksDTOS]');
                }
                delete r['saved'];
                if (r.powerOfInfluence && r.probability && r.reactionOnRisks && r.notationConcernRisk && r.responsiblePerson && r.stateForDay) {
                    // console.log('deleted empty commercial Risk');
                    temporary.push(r);
                }
            });
            this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS = temporary;
        }
        // console.log(this.send);
    }

    public setSavedFieldInSendFile(savedValue: boolean, _send: any): any {
        console.log(_send);
        const send = _send;
        send.measureUnitsPurposesDTOS.forEach((measure) => {
            if (measure.filledMeasureUnitsDTOS && measure.filledMeasureUnitsDTOS.length) {
                measure.filledMeasureUnitsDTOS.forEach((filledMeasure) => {
                    filledMeasure.saved = savedValue;
                    filledMeasure.saved = savedValue;
                });
            }
        });
        console.log(send.risksPurposesDTOS);
        send.risksPurposesDTOS.forEach((measure) => {
            if (measure.filledRisksDTOS && measure.filledRisksDTOS.length) {
                measure.filledRisksDTOS.forEach((filledRisk) => {
                    filledRisk.saved = savedValue;
                    filledRisk.saved = savedValue;
                });
            }
        });
        send.commercialRisksPurposesDTO.filledCommercialRisksDTOS.forEach((filledCommercialRisk) => {
            filledCommercialRisk.saved = savedValue;
            filledCommercialRisk.saved = savedValue;
        });
        console.log(send);
        return send;
    }

    public deleteEmptyObjectsFromSendJSON2(_send: any): any {
        const send = _send;
        send.measureUnitsPurposesDTOS.forEach((item) => {
            item['filledMeasureUnitsDTOS'].forEach((m, index) => {
                for (const key in m) {
                    if (void 0 === m[key]) {
                        if (!m.hasOwnProperty('baseValue') || m['baseValue'] === void 0) {
                            delete item['filledMeasureUnitsDTOS'][index];
                        }
                    }
                }
            })
        });
        send.measureUnitsPurposesDTOS.forEach((item) => {
            item['filledMeasureUnitsDTOS'] = item['filledMeasureUnitsDTOS'].filter((m) => {
                return m !== null;
            });
        });
        send.risksPurposesDTOS.forEach((item) => {
            item['filledRisksDTOS'].forEach((m, index) => {
                for (const key in m) {
                    if (void 0 === m[key]) {
                        if (!m.hasOwnProperty('powerOfInfluence') || m['powerOfInfluence'] === void 0) {
                            delete item['filledRisksDTOS'][index];
                        }
                    }
                }
            })
        });
        send.risksPurposesDTOS.forEach((item) => {
            item['filledRisksDTOS'] = item['filledRisksDTOS'].filter((m) => {
                return m !== null;
            });
        });
        return send;
    }

    public submitSendPlanFunction(saveButtonPressed: boolean) {
        if (this.purposesAreChecked && this.purposesBecomePlans && !this.plansAreSendAndUnchecked) {
            // console.log(this.send);
            const temporary1 = this.setSavedFieldInSendFile(saveButtonPressed, this.send);
            // const temporary2 = temporary1;
            const temporary2 = this.removeEmptyHighRisk(temporary1);
            console.log(temporary2);
            if (saveButtonPressed) {
                temporary2.statusOfSending = this.statusOfSending;
                this.makeRequestForSendSaveJSON(saveButtonPressed, temporary2);
            }
            if (!saveButtonPressed) {
                if (this.validateSendFileMeasure() && this.validateSendFileRisk() && this.validateSendFileCommercialRisks()
                    && this.validateSendFileCommRisksPopupChecked() && this.validateSendFileRisksPopupChecked() && this.validateSendFileTextareaFilled()) {
                    console.log('sending ((');
                    const temporary3 = this.deleteEmptyObjectsFromSendJSON2(temporary2);
                    this.makeRequestForSendSaveJSON(saveButtonPressed, temporary3);
                }
            }
        } else  {
            return false;
        }
    }

    replaceRiskDateobjToStringFromDatepicker(send: any): any {
        send.risksPurposesDTOS.forEach((risksPurpose) => {
            risksPurpose.filledRisksDTOS.forEach((risk) => {
                if (risk.stateForDay && risk.stateForDay.year && risk.stateForDay.month && risk.stateForDay.day)  {
                    const dateObj = risk.stateForDay;
                    // console.log(dateObj);
                    risk.stateForDay = '' + dateObj.year + '-'
                        + ((('' + dateObj.month).length > 1) ? dateObj.month : ('0' + dateObj.month)) + '-'
                        + ((('' + dateObj.day).length > 1) ? dateObj.day : ('0' + dateObj.day));
                    // console.log('risk stateForDay OBJ--->STRING');
                } else {
                    risk.stateForDay = '';
                }
            });
        });
        return send;
    }

    public removeEmptyHighRisk(send: any): any {
        (<Array<CommercialRisksDataClass>>send.commercialRisksPurposesDTO.filledCommercialRisksDTOS).forEach((risk) => {
            console.log(risk);
            let oneIsFilled: boolean;
            oneIsFilled = !!risk.highRiskDTO.analyze || !!risk.highRiskDTO.commentToHighRiskProcedure
                || !!risk.highRiskDTO.costOfListedPossibilities
                || !!risk.highRiskDTO.decisionForRiskId
                || (risk.highRiskDTO.possibilitiesToImproveRisks && risk.highRiskDTO.possibilitiesToImproveRisks.length > 0)
                || !!risk.highRiskDTO.postponePurposeAccomplishment
                // || !!risk.highRiskDTO.powerOfInfluenceToReach
                // || !!risk.highRiskDTO.probabilityToReach
                || !!risk.highRiskDTO.projectedTermDeployFinish
                || !!risk.highRiskDTO.projectedTermDeployStart
                || !!risk.highRiskDTO.rejectToAccomplishPurpose
                || !!risk.highRiskDTO.restrictRangeOfPurposeAccomplishment
                || !!risk.highRiskDTO.substantiationForAnalyze;

            if (risk.highRiskDTO.possibilitiesToImproveRisks && risk.highRiskDTO.possibilitiesToImproveRisks.length === 0) {
                delete risk.highRiskDTO['possibilitiesToImproveRisks'];
                console.log('deleted possibilitiesToImproveRisks[] from commercialR');
            }
            if (!oneIsFilled) {
                delete risk['highRiskDTO'];
                console.log('deleted HighRisk from commercialR');
            }
        });

        send.risksPurposesDTOS.forEach((risksPurpose) => {
            (<Array<RisksDataClass>>risksPurpose.filledRisksDTOS).forEach((risk) => {
                let oneIsFilled: boolean;
                oneIsFilled = !!risk.highRiskDTO.analyze || !!risk.highRiskDTO.commentToHighRiskProcedure
                    || !!risk.highRiskDTO.costOfListedPossibilities
                    || !!risk.highRiskDTO.decisionForRiskId
                    || (risk.highRiskDTO.possibilitiesToImproveRisks && risk.highRiskDTO.possibilitiesToImproveRisks.length > 0)
                    || !!risk.highRiskDTO.postponePurposeAccomplishment
                    // || !!risk.highRiskDTO.powerOfInfluenceToReach
                    // || !!risk.highRiskDTO.probabilityToReach
                    || !!risk.highRiskDTO.projectedTermDeployFinish
                    || !!risk.highRiskDTO.projectedTermDeployStart
                    || !!risk.highRiskDTO.rejectToAccomplishPurpose
                    || !!risk.highRiskDTO.restrictRangeOfPurposeAccomplishment
                    || !!risk.highRiskDTO.substantiationForAnalyze;
                if (risk.highRiskDTO.possibilitiesToImproveRisks && risk.highRiskDTO.possibilitiesToImproveRisks.length === 0) {
                    delete risk.highRiskDTO['possibilitiesToImproveRisks'];
                    console.log('deleted possibilitiesToImproveRisks[] from Risk');
                }
                if (!oneIsFilled) {
                    delete risk['highRiskDTO'];
                    console.log('deleted HighRisk from Risk');
                }
            });
        });
        return send;
    }

    validateSendFileCommRisksPopupChecked(): boolean {
        let validationSatus: boolean;
        const validationArr = [];
        this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS.forEach((risk, i) => {
            validationSatus = risk.probabilities.length > 0 && risk.powerOfInfluences.length > 0;
            validationArr.push(validationSatus);

            if (this.percentageOfRisk('cr_highRisk', undefined, i) >= this.badRisk_min) {
                console.log('start checking');
                validationSatus = !!risk.highRiskDTO && !!risk.highRiskDTO.analyze && !!risk.highRiskDTO.commentToHighRiskProcedure
                && !!risk.highRiskDTO.costOfListedPossibilities
                && !!risk.highRiskDTO.decisionForRiskId
                && risk.highRiskDTO.possibilitiesToImproveRisks.length > 0
                && (risk.highRiskDTO.postponePurposeAccomplishment === true || risk.highRiskDTO.postponePurposeAccomplishment === false)
                && !!risk.highRiskDTO.powerOfInfluenceToReach
                && !!risk.highRiskDTO.probabilityToReach
                && !!risk.highRiskDTO.projectedTermDeployFinish
                && !!risk.highRiskDTO.projectedTermDeployStart
                && (risk.highRiskDTO.rejectToAccomplishPurpose === true || risk.highRiskDTO.rejectToAccomplishPurpose === false)
                && (risk.highRiskDTO.restrictRangeOfPurposeAccomplishment === true || risk.highRiskDTO.restrictRangeOfPurposeAccomplishment === false)
                && !!risk.highRiskDTO.substantiationForAnalyze;
                validationArr.push(validationSatus);
            }

        });
        // console.dir(validationArr);
        // console.log(this.send);
        // console.log('res: ' + validationArr.every((item) => item === true));
        return validationArr.every((item) => item === true);
    }
    validateSendFileRisksPopupChecked(): boolean {
        const validationArr = [];
        this.send.risksPurposesDTOS.forEach((risksPurpose, p_index) => {
            risksPurpose.filledRisksDTOS.forEach((risk, i) => {
                if (risk.probabilities.length > 0 && risk.powerOfInfluences.length > 0) {
                    validationArr.push(true);
                    if (this.percentageOfRisk('r_highRisk', p_index, i) >= this.badRisk_min) {
                        if (!!risk.highRiskDTO && !!risk.highRiskDTO.analyze && !!risk.highRiskDTO.commentToHighRiskProcedure
                            && !!risk.highRiskDTO.costOfListedPossibilities
                            && !!risk.highRiskDTO.decisionForRiskId
                            && risk.highRiskDTO.possibilitiesToImproveRisks.length > 0
                            && (risk.highRiskDTO.postponePurposeAccomplishment === true || risk.highRiskDTO.postponePurposeAccomplishment === false)
                            && !!risk.highRiskDTO.powerOfInfluenceToReach
                            && !!risk.highRiskDTO.probabilityToReach
                            && !!risk.highRiskDTO.projectedTermDeployFinish
                            && !!risk.highRiskDTO.projectedTermDeployStart
                            && (risk.highRiskDTO.rejectToAccomplishPurpose === true || risk.highRiskDTO.rejectToAccomplishPurpose === false)
                            && (risk.highRiskDTO.restrictRangeOfPurposeAccomplishment === true || risk.highRiskDTO.restrictRangeOfPurposeAccomplishment === false)
                            && !!risk.highRiskDTO.substantiationForAnalyze) {
                            validationArr.push(true);
                        } else {validationArr.push(false); }
                    }
                } else {
                    validationArr.push(false);
                }
            });
        });
        // console.dir(validationArr);
        // console.log('res: ' + validationArr.every((item) => item === true));
        return validationArr.every((item) => item === true);
    }

    validateSendFileTextareaFilled(): boolean {
        let validationSatus: boolean;
        const validationArr = [];
        this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS.forEach((risk) => {
            validationSatus = risk.responsiblePerson && risk.responsiblePerson.length > 0
                && risk.notationConcernRisk && risk.notationConcernRisk.length > 0;
            validationArr.push(validationSatus);
        });
        this.send.risksPurposesDTOS.forEach((riskPurposes) => {
            riskPurposes.filledRisksDTOS.forEach((filledRisk) => {
                validationSatus = filledRisk.responsiblePerson && filledRisk.responsiblePerson.length > 0
                    && filledRisk.notationConcernRisk && filledRisk.notationConcernRisk.length > 0;
                validationArr.push(validationSatus);
            });
        });
        // console.dir(validationArr);
        // console.log('res: ' + validationArr.every((item) => item === true));
        return validationArr.every((item) => item === true);
    }
    makeRequestForSendSaveJSON(saveButtonPressed: boolean, send: any) {
        this.formIsSaving = true;
        console.log(send);
        // console.log(this.temp_highRisk);
        // let _send = send;
        // const _p_index = this.temp_highRisk['0']._p_index;
        // const _i = this.temp_highRisk['0']._i;
        // const data = this.temp_highRisk['0'].r_highRisk;
        // console.log(_p_index, _i, data);
        //
        // // _send.risksPurposesDTOS[_p_index].filledRisksDTOS[_i].highRiskDTO.analyze = data.analyze;
        //
        // _send.risksPurposesDTOS["0"].filledRisksDTOS["0"].highRiskDTO.analyze = data.analyze;
        // Object.defineProperty(_send.risksPurposesDTOS[_p_index].filledRisksDTOS[_i], 'highRisk', {
        //     value: new HighRiskDataModel(data.analyze,
        //         data.commentToHighRiskProcedure,
        //         data.costOfListedPossibilities,
        //         data.decisionForRiskId,
        //         data.possibilitiesToImproveRisks,
        //         data.postponePurposeAccomplishment,
        //         data.powerOfInfluenceToReach,
        //         data.probabilityToReach,
        //         data.projectedTermDeployFinish,
        //         data.projectedTermDeployStart,
        //         data.rejectToAccomplishPurpose,
        //         data.restrictRangeOfPurposeAccomplishment,
        //         data.substantiationForAnalyze),
        //     writable: true,
        //     enumerable: true,
        //     configurable: true
        // });
        // console.log('::::::');
        // console.log(_send);


        this.ordersService.sendPlansFromUser(this.order_id, send)
            .takeWhile(() => this.alive)
            .subscribe(
                (res: any) => {
                    console.log(res.status);
                    if (res.status === 200) {
                        if (!saveButtonPressed) {
                            this.plansAreSendAndUnchecked = true;
                        } else {
                            console.log('saveButtonPressed');
                        }
                        this.loadCheckedOrders(this.order_id);
                    }
                },
                (res: ResponseWrapper) => {
                    this.formIsSaving = false;
                    // this.validationError += 'status ' + res.status + ', ' + ((res.statusText) ? res.statusText : '');
                    this.onError(res.json);

                }
            );
    }

    validateSendFileMeasure(): boolean {
        if (!this.send.measureUnitsPurposesDTOS['0'].length && !this.send.measureUnitsPurposesDTOS['0'].filledMeasureUnitsDTOS['0'].length) {
            return true;
        }
        this.deleteEmptyObjectsFromSendJSON1();
        const obj = this.send;
        for (const key1 in obj) {
            if (obj.hasOwnProperty(key1)) {
                if (obj.id && obj.ids && obj.ids.length && obj.statusOfSending) {
                    let measureCompleted: boolean;

                    obj.measureUnitsPurposesDTOS.forEach((purpose) => {
                        for (const ms in purpose) {
                            if (purpose.hasOwnProperty(ms)) {
                                if (purpose.filledMeasureUnitsDTOS && purpose.filledMeasureUnitsDTOS.length) {
                                    purpose.filledMeasureUnitsDTOS.forEach((measure) => {
                                        if (measure.baseValue && measure.actualValue && measure.finalValue && measure.purposeAccomplishmentStatus
                                            && measure.costOfPurposeRealisation) {
                                            // glossaryOfMeasureUnitsDTO: {id: number, name: string}
                                            measureCompleted = true;
                                        } else {
                                            measureCompleted = false;
                                        }
                                    });
                                }
                            }
                        }
                    });

                    if (measureCompleted) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    validateSendFileRisk(): boolean {
        if (!this.send.risksPurposesDTOS['0'].length && !this.send.risksPurposesDTOS['0'].filledRisksDTOS['0'].length) {
            return true;
        }
        let message: string;
        this.deleteEmptyObjectsFromSendJSON1();
        const obj = this.send;
        for (const key1 in obj) {
            if (obj.hasOwnProperty(key1)) {
                if (obj.id && obj.ids && obj.ids.length && obj.statusOfSending) {
                    let riskCompleted: boolean;

                    obj.risksPurposesDTOS.forEach((purpose, i_purpose) => {
                        for (const ms in purpose) {
                            if (purpose.hasOwnProperty(ms)) {
                                if (purpose.filledRisksDTOS && purpose.filledRisksDTOS.length) {
                                    purpose.filledRisksDTOS.forEach((risk, i_risk) => {
                                        if (risk.risksPurposesId && risk.powerOfInfluence && risk.probability && risk.reactionOnRisks
                                            && risk.notationConcernRisk && risk.strengthOfControlFunctionProbability && risk.strengthOfControlFunctionPowerOfInfluence
                                            && risk.responsiblePerson && risk.stateForDay) {
                                            if (risk.probabilities && (risk.probabilities.length > 0) && risk.powerOfInfluences && (risk.powerOfInfluences.length > 0)) {
                                                riskCompleted = true;
                                            } else {
                                                riskCompleted = false;
                                                if (!risk.probabilities.length) {
                                                    message += 'Purpose ' + i_purpose + ', empty Probabilities at line ' + i_risk;
                                                }
                                                if (!risk.powerOfInfluences.length) {
                                                    message += 'Purpose ' + i_purpose + ', empty powerOfInfluences at line ' + i_risk;
                                                }
                                            }
                                        } else {
                                            riskCompleted = false;
                                        }
                                    });
                                }
                            }
                        }
                    });

                    if (riskCompleted) {
                        this.validationError = '';
                        return true;
                    } else {
                        this.validationError = message;
                    }
                }
            }
        }
        return false;
    }
    validateSendFileCommercialRisks(): boolean {
        if (!this.commercialRisks.length) {
            return true;
        }
        this.deleteEmptyObjectsFromSendJSON1();
        const obj = this.send;
        for (const key1 in obj) {
            if (obj.hasOwnProperty(key1)) {
                if (obj.id && obj.ids && obj.ids.length && obj.statusOfSending) {
                    let commercialRisk: boolean;

                    for (const comR in obj.commercialRisksPurposesDTO) {
                        if (obj.commercialRisksPurposesDTO.hasOwnProperty(comR)) {
                            if (obj.commercialRisksPurposesDTO.filledCommercialRisksDTOS && obj.commercialRisksPurposesDTO.filledCommercialRisksDTOS.length) {
                                let filledCommercialRisksDTOS: Array<CommercialRisksDataClass>;
                                filledCommercialRisksDTOS = obj.commercialRisksPurposesDTO.filledCommercialRisksDTOS;
                                filledCommercialRisksDTOS.forEach((cr) => {
                                    if (cr.commercialRiskPurposesId && cr.powerOfInfluence && cr.probability && cr.reactionOnRisks
                                        && cr.notationConcernRisk && cr.strengthOfControlFunctionProbability && cr.strengthOfControlFunctionPowerOfInfluence
                                        && cr.responsiblePerson && cr.stateForDay) {
                                        // glossaryOfCommercialRisksDTO: {id: number}
                                        commercialRisk = true;
                                    } else {
                                        commercialRisk = false;
                                    }
                                });
                            }
                        }
                    }
                    if (commercialRisk) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    setSendObject(property: string, _value: any) {
        Object.defineProperty(this.send, property, {
            value: _value,
            writable: true,
            enumerable: true,
            configurable: true
        });
    }

    public selectRisksProbabilityClicked(selectValue: any, type: string, p_index: number, i: number) {
        console.log('select clicked');
        // if (!this.popupCheckboxesActiveType) { // added
        this._p_index = p_index;
        this._i = i;
        this.popupCheckboxesActiveType = type;
        // this.popupCheckboxesActiveValue = selectValue;
        console.log(selectValue);
        // this.validateSelectWithPopup(type, p_index, i);
        if (selectValue) {
            this._p_index = (selectValue) ? p_index : undefined;
            this._i = (selectValue) ? i : undefined;
            switch (type) {
                case 'r_probabilities':
                    this.setCheckboxesToDisplay_probabilities(selectValue);
                    this.checkboxesChecked = this.send.risksPurposesDTOS[this._p_index].filledRisksDTOS[this._i].probabilities;
                    break;
                case 'cr_probabilities':
                    this.setCheckboxesToDisplay_probabilities(selectValue);
                    this.checkboxesChecked = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[this._i].probabilities;
                    break;
                case 'r_powerOfInfluence':
                    this.setCheckboxesToDisplay_powerOfInfluence(selectValue);
                    this.checkboxesChecked = this.send.risksPurposesDTOS[this._p_index].filledRisksDTOS[this._i].powerOfInfluences;
                    break;
                case 'cr_powerOfInfluence':
                    this.setCheckboxesToDisplay_powerOfInfluence(selectValue);
                    this.checkboxesChecked = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[this._i].powerOfInfluences;
                    break;
                default:
                    console.log('we have problems!');
                    break;
            }
            console.log(this.checkboxesChecked);
        } else {this.closePopupCheckboxes(true); }
        console.log(this._i, i);
        // } else {
        //     this.closePopupCheckboxes(true);
        // }

    }

    public selectProbabilityCheckedHandler(type: string, p_index: number, i: number) {
        console.log('value changed, empty it');
        let selectValue: number;
        switch (type) {
            case 'r_probabilities':
                selectValue = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].probability;
                break;
            case 'cr_probabilities':
                selectValue = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].probability;
                break;
            case 'r_powerOfInfluence':
                selectValue = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].powerOfInfluence;
                break;
            case 'cr_powerOfInfluence':
                selectValue = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].powerOfInfluence;
                break;
            default:
                console.log('we have problems!');
                break;
        }
        console.log(p_index, i);
        this._i = (selectValue) ? i : undefined;
        console.log(selectValue, type, i, this._i);
        console.log(this.send.commercialRisksPurposesDTO);
        if (selectValue) {
            switch (type) {
                case 'r_probabilities':
                    if (this.checkboxesProbabilitiesAll.length) {
                        this.setCheckboxesToDisplay_probabilities(selectValue);
                        this.checkboxesChecked = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].probabilities;
                        this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].probabilities = [];
                        console.log('old data is cleared');
                    }
                    break;
                case 'cr_probabilities':
                    if (this.checkboxesProbabilitiesAll.length) {
                        this.setCheckboxesToDisplay_probabilities(selectValue);
                        this.checkboxesChecked = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].probabilities;
                        this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].probabilities = [];
                        console.log('old data is cleared');
                    }
                    break;
                case 'r_powerOfInfluence':
                    if (this.checkboxesPowerOfInfluenceAll.length) {
                        this.setCheckboxesToDisplay_powerOfInfluence(selectValue);
                        this.checkboxesChecked = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].powerOfInfluences;
                        this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].powerOfInfluences = [];
                        console.log('old data is cleared');
                    }
                    break;
                case 'cr_powerOfInfluence':
                    if (this.checkboxesPowerOfInfluenceAll.length) {
                        this.setCheckboxesToDisplay_powerOfInfluence(selectValue);
                        this.checkboxesChecked = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].powerOfInfluences;
                        this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].powerOfInfluences = [];
                        console.log('old data is cleared');
                    }
                    break;
                default:
                    console.log('we have problems!');
                    break;
            }
        }

        this._p_index = (selectValue) ? p_index : undefined;
        this._i = (selectValue) ? i : undefined;
    }

    public badRiskFunction(type: string, p_index: number, i: number) {
        console.log('open');
        this.popupCheckboxesActiveType = type;
        this.highRisk_popupTitle = 'PLAN POSTĘPOWANIA Z RYZYKIEM BARDZO WYSOKIM';
        switch (type) {
            case 'r_highRisk':
                this.highRisk_popupPurpose = this.glossaryOfPurposes[p_index].name;
                this.riskToDisplay = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i];
                console.log(this.riskToDisplay);
                this.comRiskToDisplay = new CommercialRisksDataClass(undefined, undefined, undefined, undefined,
                    undefined, {id: undefined, name: undefined}, undefined,
                    [], undefined, [], false, undefined, undefined,
                    new HighRiskDataModel(undefined, undefined, undefined, undefined,
                        undefined, undefined, undefined, undefined,
                        undefined, undefined, undefined, undefined, undefined));
                this.badRiskReached_cr = false;
                this.badRiskReached_r = true;
                break;
            case 'cr_highRisk':
                this.highRisk_popupPurpose = '';
                this.comRiskToDisplay = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i];
                this.riskToDisplay = new RisksDataClass(undefined, undefined, undefined, undefined, undefined, {id: undefined, name: undefined},
                    undefined, [], undefined, [], false, undefined, undefined,
                    new HighRiskDataModel(undefined, undefined, undefined, undefined,
                        undefined, undefined, undefined, undefined,
                        undefined, undefined, undefined, undefined, undefined));
                this.badRiskReached_r = false;
                this.badRiskReached_cr = true;
                console.log(this.comRiskToDisplay);
                break;
            default:
                console.log('we have problems!');
                break;
        }
        console.log(p_index, i);
        this._i = i;
        this._p_index = p_index;
    }

    public setCheckboxesToDisplay_probabilities(selectValue: number) {
        this.checkboxesToDisplay = this.checkboxesProbabilitiesAll.filter((obj) => {
            return obj.number === selectValue;
        });
    }
    public setCheckboxesToDisplay_powerOfInfluence(selectValue: number) {
        this.checkboxesToDisplay = this.checkboxesPowerOfInfluenceAll.filter((obj) => {
            return obj.number === selectValue;
        });
    }

    public setNewValue(checkedCheckboxes: Array<number>) {
        if (!this.plansAreSendAndUnchecked) {
            switch (this.popupCheckboxesActiveType) {
                case 'r_probabilities':
                    this.send.risksPurposesDTOS[this._p_index].filledRisksDTOS[this._i].probabilities = checkedCheckboxes;
                    break;
                case 'cr_probabilities':
                    this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[this._i].probabilities = checkedCheckboxes;
                    break;
                case 'r_powerOfInfluence':
                    this.send.risksPurposesDTOS[this._p_index].filledRisksDTOS[this._i].powerOfInfluences = checkedCheckboxes;
                    break;
                case 'cr_powerOfInfluence':
                    this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[this._i].powerOfInfluences = checkedCheckboxes;
                    break;
                default:
                    console.log('we have problems!');
                    break;
            }
            // this.validateSelectWithPopup(this.popupCheckboxesActiveType, this._p_index, this._i);
        } else {console.log(')'); }
        console.log(this.send);
    }

    public setNewHighRisk(data: HighRiskDataModel) {
        // if (!this.plansAreSendAndUnchecked) {
        console.log(this._p_index, this._i);
        switch (this.popupCheckboxesActiveType) {
            case 'r_highRisk':
                console.log(this.popupCheckboxesActiveType);
                this.send.risksPurposesDTOS[this._p_index].filledRisksDTOS[this._i].highRiskDTO = data;
                // Object.defineProperty(this.send.risksPurposesDTOS[this._p_index].filledRisksDTOS[this._i], 'highRisk', {
                //     value: new HighRiskDataModel(data.analyze,
                //         data.commentToHighRiskProcedure,
                //         data.costOfListedPossibilities,
                //         data.decisionForRiskId,
                //         data.possibilitiesToImproveRisks,
                //         data.postponePurposeAccomplishment,
                //         data.powerOfInfluenceToReach,
                //         data.probabilityToReach,
                //         data.projectedTermDeployFinish,
                //         data.projectedTermDeployStart,
                //         data.rejectToAccomplishPurpose,
                //         data.restrictRangeOfPurposeAccomplishment,
                //         data.substantiationForAnalyze),
                //     writable: true,
                //     enumerable: true,
                //     configurable: true
                // });
                this.temp_highRisk.push({'r_highRisk': data,
                    '_p_index': this._p_index,
                    '_i': this._i});
                break;
            case 'cr_highRisk':
                console.log(this.popupCheckboxesActiveType);
                this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[this._i].highRiskDTO = data;
                break;
            default:
                console.log('we have problems!');
                break;
        }
        // this.validateSelectWithPopup(this.popupCheckboxesActiveType, this._p_index, this._i);
        // } else {console.log(')'); }
        console.log(this.send);
    }
    validateSelectWithPopup(type: string, p_index: number, i: number) {
        let selectClicked: any;
        let checkedCheckboxes: Array<number>;
        console.log(type, p_index, i);
        switch (type) {
            case 'r_probabilities':
                selectClicked = window.document.getElementById('select_risks_probability_' + p_index + i);
                checkedCheckboxes = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].probabilities;
                break;
            case 'cr_probabilities':
                selectClicked = window.document.getElementById('select_comrisks_probability_' + i);
                checkedCheckboxes = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].probabilities;
                break;
            case 'r_powerOfInfluence':
                selectClicked = window.document.getElementById('select_risks_influence_' + p_index + i);
                checkedCheckboxes = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].powerOfInfluences;
                break;
            case 'cr_powerOfInfluence':
                selectClicked = window.document.getElementById('select_comrisks_influence_' + i);
                checkedCheckboxes = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].powerOfInfluences;
                break;
            default:
                console.log('we have problems!');
                break;
        }
        console.dir(selectClicked);
        console.log(checkedCheckboxes);
        if (selectClicked) {
            if (!checkedCheckboxes.length) {
                selectClicked.classList.add('popupCheckboxesIsEmpty');
                console.log('ADDED:');
                console.dir(selectClicked);
            } else {
                if (selectClicked && selectClicked.classList.contains('popupCheckboxesIsEmpty')) {
                    selectClicked.classList.remove('popupCheckboxesIsEmpty');
                    console.log('removed');
                }
            }
        }
    }
    transformRDateForJSON(p_index: number, i: number, dateM: any) {
        console.log(dateM, p_index, i);

        if (dateM && dateM.year && dateM.month && dateM.day) { // doesn't work
            this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].stateForDay = '' + dateM.year + '-'
                + ((('' + dateM.month).length > 1) ? dateM.month : ('0' + dateM.month)) + '-'
                + ((('' + dateM.day).length > 1) ? dateM.day : ('0' + dateM.day));
        }
        console.log(this.send);
        // this._send.risksPurposesDTOS[p_index].filledRisksDTOS[i].stateForDay = dateM;
        console.log(this._send);
    }
    transformCRDateForJSON(i: number, dateM: any) {
        console.log(dateM);
        if (dateM && dateM.year && dateM.month && dateM.day) {
            this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i]
                .stateForDay = '' + dateM.year + '-'
                + ((('' + dateM.month).length > 1) ? dateM.month : ('0' + dateM.month)) + '-'
                + ((('' + dateM.day).length > 1) ? dateM.day : ('0' + dateM.day));
        }
        console.log(this.send.commercialRisksPurposesDTO);
        console.log(this._send);
    }
    closePopupCheckboxes(close: boolean) {
        if (close) {
            console.log('closed');
            this._p_index = undefined;
            this._i = undefined;
            this.popupCheckboxesActiveType = undefined;
            this.popupCheckboxesActiveValue = undefined;
            this.badRiskReached_cr = false;
            this.badRiskReached_r = false;
        }
    }

    public setMinProbability() {
        const arrayNumbers = this.selectCR_probability_values.filter((o) => (typeof o.value) === (typeof 1));
        this.minProbability = Math.min.apply(Math, arrayNumbers.map(function(o) {
            return +o.value;
        }));
        // console.log('minProbability ' + this.minProbability);
    }
    public setMaxProbability() {
        const arrayNumbers = this.selectCR_probability_values.filter((o) => (typeof o.value) === (typeof 1));
        this.maxProbability = Math.max.apply(Math, arrayNumbers.map(function(o) {
            return +o.value;
        }));
        // console.log('maxProbability ' + this.maxProbability);
    }
    public setMinPowerOfInfluence() {
        const arrayNumbers = this.selectCR_influence_values.filter((o) => (typeof o.value) === (typeof 1));
        this.minPowerOfInfluence = Math.min.apply(Math, arrayNumbers.map(function(o) {
            return +o.value;
        }));
        // console.log('minPowerOfInfluence ' + this.minPowerOfInfluence);
    }
    public setMaxPowerOfInfluence() {
        const arrayNumbers = this.selectCR_influence_values.filter((o) => (typeof o.value) === (typeof 1));
        this.maxPowerOfInfluence = Math.max.apply(Math, arrayNumbers.map(function(o) {
            return +o.value;
        }));
        // console.log('maxPowerOfInfluence ' + this.maxPowerOfInfluence);
    }

    public highRiskAchived(probability: number, powerOfInfluence: number): boolean {
        return (probability * powerOfInfluence === this.maxProbability * this.maxPowerOfInfluence);
    }

    levelOfRisk(type: string, p_index: number, i: number): number {
        let probability: number;
        let powerOfInfluence: number;
        let SMKP: number;
        let SMKS: number;
        // console.log(this.popupCheckboxesActiveType, p_index, i);
        switch (type) {
            case 'r_highRisk':
                probability = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].probability;
                powerOfInfluence = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].powerOfInfluence;
                SMKP = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].strengthOfControlFunctionProbability;
                SMKS = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].strengthOfControlFunctionPowerOfInfluence;
                break;
            case 'cr_highRisk':
                probability = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].probability;
                powerOfInfluence = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].powerOfInfluence;
                SMKP = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].strengthOfControlFunctionProbability;
                SMKS = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].strengthOfControlFunctionPowerOfInfluence;
                break;
            default: console.log('we have problems!');
                break;
        }
        // console.log(((probability - SMKP + 1) > 0 ? (probability - SMKP + 1) : 1) * ((powerOfInfluence - SMKS + 1) > 0 ? (powerOfInfluence - SMKS + 1) : 1));
        return ((probability - SMKP + 1) > 0 ? (probability - SMKP + 1) : 1) * ((powerOfInfluence - SMKS + 1) > 0 ? (powerOfInfluence - SMKS + 1) : 1);
    }
    percentageOfRisk(type: string, p_index: number, i: number): number {
        let probability: number;
        let powerOfInfluence: number;
        let SMKP: number;
        let SMKS: number;
        switch (type) {
            case 'r_highRisk':
                probability = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].probability;
                powerOfInfluence = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].powerOfInfluence;
                SMKP = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].strengthOfControlFunctionProbability;
                SMKS = this.send.risksPurposesDTOS[p_index].filledRisksDTOS[i].strengthOfControlFunctionPowerOfInfluence;
                break;
            case 'cr_highRisk':
                probability = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].probability;
                powerOfInfluence = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].powerOfInfluence;
                SMKP = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].strengthOfControlFunctionProbability;
                SMKS = this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS[i].strengthOfControlFunctionPowerOfInfluence;
                break;
            default: console.log('we have problems!');
                break;
        }
        const actual = ((probability - SMKP + 1) > 0 ? (probability - SMKP + 1) : 1) * ((powerOfInfluence - SMKS + 1) > 0 ? (powerOfInfluence - SMKS + 1) : 1);
        const ceiling = this.maxProbability * this.maxPowerOfInfluence;
        return ((actual / ceiling) * 100);
    }

}
