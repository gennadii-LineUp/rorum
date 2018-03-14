import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {ResponseWrapper} from '../../shared';
import {GlossaryOfPurposesService} from '../glossary-of-purposes';
import {GlossaryOfPurposes} from '../glossary-of-purposes/glossary-of-purposes.model';
import {Orders, OrdersService} from '../orders';
import {SetOfSentPurposesService} from './set-of-sent-purposes.service';
import 'rxjs/add/operator/takeWhile';
import {Observable} from 'rxjs/Observable';
import {CommercialRisksDataClass} from '../orders/models/commercial-risks-data.model';
import {MeasuresDataClass} from '../orders/models/measures-data.model';
import {RisksDataClass} from '../orders/models/risks-data.model';
import {MeasuresFrontClass} from '../orders/models/measures-front.model';
import {RisksFrontClass} from '../orders/models/risks-front.model';
import {SendDataClass} from '../orders/models/send-data.model';
import {OrganisationStructureService} from '../organisation-structure/organisation-structure.service';
import {CheckboxesFromSelectDataModel} from '../orders/models/checkboxes-from-select-data.model';
import {HighRiskDataModel} from '../orders/models/high-risk-data.model';

@Component({
    selector: 'jhi-set-purposes-check',
    templateUrl: './set-of-sent-purposes-check.component.html',
    styleUrls: ['../orders/order-cele.component.css']
})
export class SetOfSentPurposesCheckComponent implements OnInit, OnDestroy {
    glossaryOfPurposes: GlossaryOfPurposes[];
    purposesToCheck = [];
    purposesChecked = [];
    set_purposes_id: number;
    statusOfSending: string;
    order_id: number;
    purposesBecomePlans = false;
    celeAreConfirmed = false;
    plansAreConfirmed = false;
    order: Orders;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    alive = true;
    status: Observable<boolean>;
    parent_type: string;
    p_index: number;
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
        'measureUnitsPurposesDTOS': [
            {
                'glossaryOfPurposesId': 0, // --------------чекнутая цель
                'filledMeasureUnitsDTOS': Array<MeasuresFrontClass>(),
            }
        ],
        'risksPurposesDTOS': [
            {
                'glossaryOfPurposesId': 0, // --------------чекнутая цель
                'filledRisksDTOS': Array<RisksFrontClass>()
            }
        ]
    };
    measures = [];
    risks = [];
    commercialRisks = [];
    setOfSentPurposesId: number;
    commercialRiskPurposesId: number;
    setOfSentPurposes: any;
    purposesAreChecked = false;
    plansAreSendAndUnchecked = true;
    checkboxesProbabilitiesAll: Array<CheckboxesFromSelectDataModel>;
    checkboxesToDisplay: Array<CheckboxesFromSelectDataModel>;
    checkboxesChecked: Array<number>;
    checkboxesPowerOfInfluenceAll: Array<CheckboxesFromSelectDataModel>;
    _p_index: number;
    _i: number;
    popupCheckboxesActiveType: string;
    popupCheckboxesActiveValue: number;

    selectM_baseValue_values = [
        { value: undefined, display: '' },
        { value: 1, display: ' 1 '},
        { value: 2, display: ' 2 '},
        { value: 3, display: ' 3 '},
        { value: 4, display: ' 4 '},
        { value: 5, display: ' 5 '},
    ];
    selectM_actualValue_values = [
        { value: undefined, display: '' },
        { value: 1, display: ' 1 '},
        { value: 2, display: ' 2 '},
        { value: 3, display: ' 3 '},
        { value: 4, display: ' 4 '},
        { value: 5, display: ' 5 '},
    ];
    selectM_finalValue_values = [
        { value: undefined, display: '' },
        { value: 1, display: ' 1 '},
        { value: 2, display: ' 2 '},
        { value: 3, display: ' 3 '},
        { value: 4, display: ' 4 '},
        { value: 5, display: ' 5 '},
    ];
    selectM_purposeAccomplished_active = '';
    selectM_purposeAccomplished_values = [
        { value: undefined, display: '' },
        { value: '_1_25',  display: ' 1-25' },
        { value: '_26_50', display: '26-50' },
        { value: '_51_99', display: '51-99' },
        { value: '_100', display: '100'}
    ];
    selectCR_probability_active = '';
    selectCR_probability_values = [
        { value: undefined, display: '' },
        {value: 1, display: 'Never'},
        {value: 2, display: 'One time a year'},
        {value: 3, display: 'One time per month'},
        {value: 4, display: 'One time per week'},
        {value: 5, display: 'Very often'},
    ];
    selectCR_influence_active = '';
    selectCR_influence_values = [
        { value: undefined, display: '' },
        {value: 1, display: 'Never'},
        {value: 2, display: 'One time a year'},
        {value: 3, display: 'One time per month'},
        {value: 4, display: 'One time per week'},
        {value: 5, display: 'Very often'},
    ];
    selectCR_reactionOnRisks_active = '';
    selectCR_reactionOnRisks_values = [
        { value: undefined, display: '' },
        { value: 'ACCEPTATION_RISK',  display: 'ACCEPTATION_RISK' },
        { value: 'SHARE_RISK', display: 'SHARE_RISK' },
        { value: 'AVOID_RISK', display: 'AVOID_RISK' },
        { value: 'RESTRICT_RISK', display: 'RESTRICT_RISK' },
        { value: 'REINFORCEMENT', display: 'REINFORCEMENT'},
        { value: 'USAGE', display: 'USAGE'}
    ];
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
    riskToDisplay: RisksDataClass;
    comRiskToDisplay: CommercialRisksDataClass;
    badRiskReached_cr = false;
    badRiskReached_r = false;
    highRisk_popupTitle = '';
    highRisk_popupPurpose = '';

    constructor(
        private eventManager: JhiEventManager,
        private ordersService: OrdersService,
        private setOfSentPurposesService: SetOfSentPurposesService,
        private glossaryOfPurposesService: GlossaryOfPurposesService,
        private organisationStructureService: OrganisationStructureService,
        private jhiAlertService: JhiAlertService,
        private route: ActivatedRoute,
        private router: Router

    ) {
        setOfSentPurposesService.status$
            .takeWhile(() => this.alive)
            .subscribe((modalRejectChecking_is_closed: boolean) => {
                console.log('from component ---> ' + modalRejectChecking_is_closed);
                if (modalRejectChecking_is_closed) {
                    this.loadUsersCheckedPurposes(this.order_id, this.set_purposes_id);
                    this.router.navigate(['/orders-admin/' + this.order_id + '/set-of-sent-purposes/' + this.parent_type]);
                }
            });
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.order_id = +params['id_order'];
            this.parent_type = params['parent_type'];
            // console.log('parent_type  ' + this.parent_type);
            // console.log('order  ' + this.order_id);

            this.set_purposes_id = +params['id_set_purpose'];
            this.loadUsersCheckedPurposes(+params['id_order'], +params['id_set_purpose']);
            // console.log('set  ' + this.set_purposes_id);

            this.loadUsersAllPurposes(this.order_id, this.set_purposes_id);
            this.setOfSentPurposesService.setSetPurposesId(this.set_purposes_id);
            this.setOfSentPurposesService.setOrderId(this.order_id);
            this.setOfSentPurposesService.setParentType(this.parent_type);
        });
        this.registerChangeInOrderss();
        this.getListOfPowerOfInfluenceForPopup();
        this.getListOfProbabilitiesForPopup();
        // this.glossaryOfPurposesService.query()
        this.setMinProbability();
        this.setMaxProbability();
        this.setMinPowerOfInfluence();
        this.setMaxPowerOfInfluence();
    }

    ngOnDestroy() {
        console.log('SetOfSentPurposesCheckComponent destroyed');
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
        this.alive = false;
    }

    loadUsersCheckedPurposes(id_order: number, id_set_purpose: number) {
        this.setOfSentPurposesService.getSetPurposes(id_order, id_set_purpose)
            .takeWhile(() => this.alive)
            .subscribe(
                (res: any) => {

                    console.log('---FROM SERVER: --');
                    console.log(res);
                    this.statusOfSending = res.statusOfSending;
                    this.setOfSentPurposesService.setStatusOfSending(this.statusOfSending);
                    const st = res.statusOfSending;
                    this.purposesChecked = res.ids;
                    this.celeAreConfirmed = ((st === 'CONFIRMED') || (st === 'CONFIRMED1') || (st === 'CONFIRMED2')
                        || (st === 'CONFIRMED_PURPOSES') || (st === 'REJECTED') || (st === 'REJECTED1')
                        || (st === 'REJECTED2') || (st === 'REJECTED_PURPOSES')
                        || (st === 'CONFIRMED_PLAN') || (st === 'REJECTED_PLAN')) ? true : false;
                    this.setOfSentPurposesService.setIds(res.ids);
                    this.plansAreConfirmed = ((st === 'CONFIRMED') || (st === 'CONFIRMED1') || (st === 'CONFIRMED2')
                        || (st === 'CONFIRMED_PURPOSES') || (st === 'REJECTED') || (st === 'REJECTED1')
                        || (st === 'REJECTED2') || (st === 'REJECTED_PURPOSES')
                        || (st === 'CONFIRMED_PLAN') || (st === 'REJECTED_PLAN')) ? true : false;

                    if ((st === 'UNCHECKED_PLAN') || (st === 'CONFIRMED_PLAN')
                        && (res.commercialRisksPurposesDTO.filledCommercialRisksDTOS.length)
                        && (res.measureUnitsPurposesDTOS["0"].filledMeasureUnitsDTOS.length)
                        && (res.risksPurposesDTOS["0"].filledRisksDTOS.length)) {
                        this.purposesBecomePlans = true;
                        this.commercialRiskPurposesId = (res.commercialRisksPurposesDTO && res.commercialRisksPurposesDTO.id) ? res.commercialRisksPurposesDTO.id : 0;
                        this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS.forEach((obj) => {
                            obj.commercialRiskPurposesId = (res.commercialRisksPurposesDTO && res.commercialRisksPurposesDTO.id) ? res.commercialRisksPurposesDTO.id : 0;
                        });
                        this.setOfSentPurposesId = res.id;
                        this.setOfSentPurposes = res;
                        this.purposesAreChecked = true;
                        this.purposesChecked = res.ids;
                        // console.log(this.purposesChecked);
                        // console.log(this.setOfSentPurposes.notation);
                        if (this.setOfSentPurposes.statusOfSending === 'REJECTED'
                            || this.setOfSentPurposes.statusOfSending === 'REJECTED1'
                            || this.setOfSentPurposes.statusOfSending === 'REJECTED2'
                            || this.setOfSentPurposes.statusOfSending === 'REJECTED_PURPOSES') {
                            this.purposesAreChecked = false;
                            this.purposesToCheck = this.purposesChecked;
                            // console.log(this.purposesToCheck);
                        } else if (this.setOfSentPurposes.statusOfSending === 'CONFIRMED_PLAN'
                            || this.setOfSentPurposes.statusOfSending === 'CONFIRMED_PURPOSES'
                            || this.setOfSentPurposes.statusOfSending === 'UNCHECKED_PLAN'
                            || this.setOfSentPurposes.statusOfSending === 'REJECTED_PLAN'
                            || this.setOfSentPurposes.statusOfSending === 'REJECTED_PLAN') {
                            this.purposesBecomePlans = true;
                            // this.createSendJSON(res);
                            this.send = res;
                            res.risksPurposesDTOS.forEach((value, index) => {
                                this.send.risksPurposesDTOS[index].filledRisksDTOS = this.loadFreshestRisksValuesFromServerForOneCheckedPurpose(value);
                            });

                            res.measureUnitsPurposesDTOS.forEach((value, index) => {
                                this.send.measureUnitsPurposesDTOS[index].filledMeasureUnitsDTOS = this.loadFreshestMEASURESValuesFromServerForOneCheckedPurpose(value);
                            });
                            this.loadCommercialRisksValuesFromServer(res);
                            this.getPercentagesOfCalculatedValuesFunction();
                            // console.log(this.send);
                        }
                    }

                    // this.purposesBecomePlans = true;
                    // statusOfSending
                    //     :
                    //     "UNCHECKED_PURPOSES"
                },
                (res: ResponseWrapper) => this.onError(res.json));
    }
    loadUsersAllPurposes(order_id: number, set_purposes_id: number) {
        this.ordersService.getListOfUsersPurposesForAdmin(order_id, set_purposes_id)
            .takeWhile(() => this.alive)
            .subscribe(
                (res: any) => {

                    this.glossaryOfPurposes = res;
                    // console.log('---- to replace: ------');
                    // console.log(this.glossaryOfPurposes);
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    // previousState() {
    //     window.history.back();
    // }

    trackId(index: number, item: GlossaryOfPurposes) {
        return item.id;
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    registerChangeInOrderss() {
        this.eventSubscriber = this.eventManager.subscribe(
            'getListOfPurposes',

            (response) => this.loadUsersCheckedPurposes(this.order_id, this.set_purposes_id)
            //  (response) => this.loadUsersCheckedPurposes(this.order.id)
        );
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

        // console.log(this.purposesToCheck);
    }
    public approveSetOfPurposesByAdminFunction() {
        if (!this.purposesBecomePlans && !this.celeAreConfirmed) {
            this.setOfSentPurposesService.approveSetOfPurposesByAdmin(this.order_id, this.set_purposes_id, this.purposesChecked)
                .takeWhile(() => this.alive)
                .subscribe(
                    (response: any) => {
                        console.log(response.status);
                        if (+response.status === 200) {
                            console.log(response);
                            const res = response.json();
                            this.statusOfSending = res.statusOfSending;
                            this.purposesChecked = res.ids;
                            this.celeAreConfirmed = (res.statusOfSending === 'CONFIRMED1' || 'CONFIRMED2' || 'CONFIRMED_PURPOSES') ? true : false;
                            this.router.navigateByUrl('/orders-admin/' + this.order_id + '/set-of-sent-purposes/' + this.parent_type);
                        }
                    },
                    (res: ResponseWrapper) => this.onError(res.json)
                );
        } else {return false; }
    }

    public approvePlansByAdminFunction() {
        if (this.purposesBecomePlans) {
            this.setOfSentPurposesService.approvePlanByAdmin(this.order_id, this.set_purposes_id, this.purposesChecked)
                .takeWhile(() => this.alive)
                .subscribe(
                    (response: any) => {
                        console.log(response.status);
                        console.log(response);
                        if (+response.status === 200) {
                            console.log(response);
                            const res = response.json();
                            this.statusOfSending = res.statusOfSending;
                            this.purposesChecked = res.ids;
                            this.plansAreConfirmed = (res.statusOfSending === 'CONFIRMED1' || 'CONFIRMED2' || 'CONFIRMED_PLAN') ? true : false;
                            this.router.navigateByUrl('/orders-admin/' + this.order_id + '/set-of-sent-purposes/' + this.parent_type);
                        }
                    },
                    (res: ResponseWrapper) => this.onError(res.json)
                );
        } else {return false; }
    }

    public accordionToggleItemFunction(e: any, purpose_id: number) {
        if (this.purposesBecomePlans) {
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
                            // console.log(this.p_index);
                        }
                    });
                    e.currentTarget.classList.add('bordered');
                    // this.getMeasureRisksFunction(purpose_id);
                } else {
                    e.currentTarget.classList.remove('bordered');
                    this.p_index = undefined;
                }
            } else {
                currentAccordionItem.className = 'accordionItem close-item';
            }
        }
    }
    showMeasuresIndex(i: any) {
        // console.log(i);
    }
    showPurpIndex(i: any) {
        // console.log(i);
    }

    createSendJSON(res: any) {
        this._send.measureUnitsPurposesDTOS = [];
        this._send.risksPurposesDTOS = [];

        res.ids.forEach((value, index) => {
            Object.defineProperty((this._send).measureUnitsPurposesDTOS, value, {
                value: {
                    'glossaryOfPurposesId': value, // --------------чекнутая цель
                    'filledMeasureUnitsDTOS': []
                },
                writable : true,
                enumerable : true,
                configurable : true});
        });
        this.send.measureUnitsPurposesDTOS = (this.send).measureUnitsPurposesDTOS.filter((value) => Object.keys(value).length !== 0);
        // this._send.measureUnitsPurposesDTOS = (this._send).measureUnitsPurposesDTOS.filter((value) => Object.keys(value).length !== 0);

        res.ids.forEach((value, index) => {
            Object.defineProperty((this._send).risksPurposesDTOS, value, {
                value: {
                    'glossaryOfPurposesId': value, // --------------чекнутая цель
                    'filledRisksDTOS': []
                },
                writable : true,
                enumerable : true,
                configurable : true});
        });
        this._send.risksPurposesDTOS = this._send.risksPurposesDTOS.filter((value) => Object.keys(value).length !== 0);

        console.log(this._send);
    }

    public loadCommercialRisksValuesFromServer(res: any) {
        const ArrayOfObjects = (res.commercialRisksPurposesDTO.filledCommercialRisksDTOS).filter((value) => {
            // console.log(value);
            return value.saved === false;
        });
        // console.log(ArrayOfObjects);
        const lastChanges_dateMs = Math.max.apply(Math, ArrayOfObjects.map(function(o){return +(new Date(o.creationDate)); }));
        const freshestObj = (ArrayOfObjects.filter(function( obj ) {
            return +(new Date(obj.creationDate)) === lastChanges_dateMs;
        }));
        // console.log(freshestObj);
        this.send.commercialRisksPurposesDTO.filledCommercialRisksDTOS = freshestObj;
    }

    public loadFreshestMEASURESValuesFromServerForOneCheckedPurpose(measureUnitsPurposesDTOS: any): Array<MeasuresDataClass> {
        const resultArray = Array<MeasuresDataClass>(0);
        const measuresId = [];
        measureUnitsPurposesDTOS.filledMeasureUnitsDTOS.forEach((value) => {
            let included = false;
            measuresId.forEach((v) => {
                if (v && (+v === +value.glossaryOfMeasureUnitsDTO.id)) {included = true; }
            });
            if (!included) {measuresId.push(value.glossaryOfMeasureUnitsDTO.id); }
        });
        // console.log(measuresId);

        let lastChanges_dateMs: number;
        measuresId.forEach((m_id) => {
            const measureHistoriesArray = (measureUnitsPurposesDTOS.filledMeasureUnitsDTOS).filter((m) => {
                return ((m.glossaryOfMeasureUnitsDTO.id === m_id) && (m.saved === false));
            });
            // console.log(measureHistoriesArray);
            if (measureHistoriesArray.length) {
                lastChanges_dateMs = Math.max.apply(Math, measureHistoriesArray.map(function(o){return +(new Date(o.creationDate)); }));
                const freshestObj = ((measureHistoriesArray.filter(function( obj ) {
                    return +(new Date(obj.creationDate)) === lastChanges_dateMs;
                })))['0'];
                // console.log(freshestObj);

                resultArray.push(new MeasuresDataClass(+freshestObj.measureUnitsPurposesId,
                    +freshestObj.baseValue,
                    +freshestObj.actualValue,
                    +freshestObj.finalValue,
                    freshestObj.purposeAccomplishmentStatus,
                    {id: freshestObj.glossaryOfMeasureUnitsDTO.id,
                        name: freshestObj.glossaryOfMeasureUnitsDTO.name,
                        unitsOfMeasurement: freshestObj.glossaryOfMeasureUnitsDTO.unitsOfMeasurement},
                    freshestObj.saved,
                    +freshestObj.costOfPurposeRealisation));
            } else {
                // resultArray.push(new MeasuresDataClass(undefined, undefined, undefined, undefined, undefined,
                //     {id: undefined, name: undefined, unitsOfMeasurement: undefined}, true, undefined));
            }
        });

        // console.log(resultArray);
        return resultArray;
    }

    public loadFreshestRisksValuesFromServerForOneCheckedPurpose(risksPurposesDTOS: any): Array<RisksDataClass> {
        const resultArray = Array<RisksDataClass>(0);
        const risksId = [];
        risksPurposesDTOS.filledRisksDTOS.forEach((value) => {
            let included = false;
            risksId.forEach((v) => {
                if (v && (+v === +value.glossaryOfRisksDTO.id)) {included = true; }
            });
            if (!included) {risksId.push(value.glossaryOfRisksDTO.id); }
        });
        // console.log(risksId);

        let lastChanges_dateMs: number;
        risksId.forEach((r_id) => {
            const riskHistoriesArray = (risksPurposesDTOS.filledRisksDTOS).filter((r) => {
                return ((r.glossaryOfRisksDTO.id === r_id) && (r.saved === false));
            });
            // console.log(riskHistoriesArray);
            if (riskHistoriesArray.length) {
                lastChanges_dateMs = Math.max.apply(Math, riskHistoriesArray.map(function(o){return +(new Date(o.creationDate)); }));
                const freshestObj = ((riskHistoriesArray.filter(function( obj ) {
                    return +(new Date(obj.creationDate)) === lastChanges_dateMs;
                })))['0'];
                // console.log(freshestObj);
                resultArray.push(new RisksDataClass( (freshestObj.risksPurposesId) ? +freshestObj.risksPurposesId : undefined,
                    (freshestObj.powerOfInfluence) ? +freshestObj.powerOfInfluence : undefined,
                    (freshestObj.probability) ? +freshestObj.probability : undefined,
                    (freshestObj.reactionOnRisks) ? freshestObj.reactionOnRisks : undefined,
                    (freshestObj.notationConcernRisk) ? freshestObj.notationConcernRisk : undefined,
                    {id: freshestObj.glossaryOfRisksDTO.id, name: freshestObj.glossaryOfRisksDTO.name},
                    (freshestObj.strengthOfControlFunctionProbability) ? freshestObj.strengthOfControlFunctionProbability : undefined,
                    freshestObj.probabilities,
                    (freshestObj.strengthOfControlFunctionPowerOfInfluence) ? freshestObj.strengthOfControlFunctionPowerOfInfluence : undefined,
                    freshestObj.powerOfInfluences,
                    freshestObj.saved,
                    (freshestObj.responsiblePerson) ? freshestObj.responsiblePerson : undefined,
                    freshestObj.stateForDay,
                    freshestObj.highRiskDTO));
            } else {
                // resultArray.push(new RisksDataClass( undefined, undefined, undefined, undefined, undefined,
                //     {id: undefined, name: undefined}, undefined, [], undefined, [], true));
            }
        });

        // console.log(resultArray);
        return resultArray;
    }

    public selectRisksProbabilityClicked(selectValue: any, type: string, p_index: number, i: number) {
        console.log('select clicked');
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
            // console.log(this.checkboxesChecked);
        } else {this.closePopupCheckboxes(true); }
        // console.log(this._i, i);
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
        // console.log(this.send.commercialRisksPurposesDTO);
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

    public setCheckboxesToDisplay_probabilities(selectValue: number) {
        this.checkboxesToDisplay = this.checkboxesProbabilitiesAll.filter((obj) => {
            return obj.number === selectValue;
        });
        // console.log(this.checkboxesToDisplay);
    }
    public setCheckboxesToDisplay_powerOfInfluence(selectValue: number) {
        this.checkboxesToDisplay = this.checkboxesPowerOfInfluenceAll.filter((obj) => {
            return obj.number === selectValue;
        });
        // console.log(this.checkboxesToDisplay);
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
