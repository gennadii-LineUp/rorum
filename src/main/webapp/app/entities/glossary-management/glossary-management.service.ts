import { Injectable } from "@angular/core";
import {SERVER_API_URL} from '../../app.constants';
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {ResponseWrapper} from "../../shared";
import {GlossaryOfProcesses} from "../glossary-of-processes";
import {GlossaryOfPurposes} from "../glossary-of-purposes";
import {GlossaryOfMeasureUnits} from "../glossary-of-measure-units";
import {Orders} from "../orders";
import {GlossaryOfKRI} from "../glossary-of-KRI";
import {GlossaryOfControlMechanisms} from "../glossary-of-control-mechanisms";
import {GlossaryOfRisks} from "../glossary-of-risks";

@Injectable()
export class GlossaryManagementService {

    private resourceUrl = SERVER_API_URL + 'api/glossary-management';
    constructor(private http: Http) { }

    //#region "Processes"

    public getAllProcesses(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/processes`)
            .map((res: Response) => {
                return this.convertResponseWithProcesses(res);
            })
    }
    private convertResponseWithProcesses(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertProcessesFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }
    private convertProcessesFromServer(json: any): GlossaryOfProcesses {
        const entity: GlossaryOfProcesses = Object.assign(new GlossaryOfProcesses(), json);
        return entity;
    }

    //#endregion "Processes"

    //#region "Purposes"

    public getAllPurposes(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/purposes`)
            .map((res: Response) => {
                return this.convertResponseWithPurposes(res);
            })
    }
    public getAllAssignmentToCellOfCurrentOrganisation(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/purposes/user`)
            .map((res: Response) => {
                return this.convertResponseWithPurposes(res);
            })
    }
    private convertResponseWithPurposes(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertPurposesFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }
    private convertPurposesFromServer(json: any): GlossaryOfPurposes {
        const entity: GlossaryOfPurposes = Object.assign(new GlossaryOfPurposes(), json);
        return entity;
    }

    //#endregion "Purposes"

    //#region "Measures"

    public getAllMeasures(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/measures`)
            .map((res: Response) => {
                return this.convertResponseWithMeasures(res);
            })
    }
    public getAllUserMeasures(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/measures/user`)
            .map((res: Response) => {
                return this.convertResponseWithMeasures(res);
            })
    }
    private convertResponseWithMeasures(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertMeasureFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }
    private convertMeasureFromServer(json: any): GlossaryOfMeasureUnits {
        const entity: GlossaryOfMeasureUnits = Object.assign(new GlossaryOfMeasureUnits(), json);
        return entity;
    }

    //#endregion "Measures"

    //#region "Orders"

    public loadAllOrders(): Observable<ResponseWrapper> {
        const resourceUserUrl = SERVER_API_URL + 'api/incident-user';
        return this.http.get(resourceUserUrl)
            .map((res: Response) => {
                return this.convertResponseWithOrders(res);
            });
    }
    private convertResponseWithOrders(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertOrdersItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }
    private convertOrdersItemFromServer(json: any): Orders {
        const entity: Orders = Object.assign(new Orders(), json);
        return entity;
    }

    //#endregion "Orders"

    //#region "Commercial Risks"

    public getAllCommercialRisks(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/commercial-risks`)
            .map((res: Response) => {
                return this.convertResponseWithCommercialRisks(res);
            })
    }
    public getAllUserCommercialRisks(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/commercial-risks/user`)
            .map((res: Response) => {
                return this.convertResponseWithCommercialRisks(res);
            })
    }
    private convertResponseWithCommercialRisks(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertCommercialRiskFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }
    private convertCommercialRiskFromServer(json: any): GlossaryOfPurposes {
        const entity: GlossaryOfPurposes = Object.assign(new GlossaryOfPurposes(), json);
        return entity;
    }

    //#endregion "Commercial Risks"

    //#region "KRI"

    public getAllKRI(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/KRI`)
            .map((res: Response) => {
                return this.convertResponseWithKRI(res);
            })
    }
    public getAllUserKRI(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/KRI/user`)
            .map((res: Response) => {
                return this.convertResponseWithKRI(res);
            })
    }
    private convertResponseWithKRI(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertKRIFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }
    private convertKRIFromServer(json: any): GlossaryOfKRI {
        const entity: GlossaryOfKRI = Object.assign(new GlossaryOfKRI(), json);
        return entity;
    }

    //#endregion "KRI"

    //#region "Control Mechanisms"

    public getAllControlMechanisms(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/control-mechanisms`)
            .map((res: Response) => {
                return this.convertResponseWithControlMechanisms(res);
            })
    }
    private convertResponseWithControlMechanisms(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertKRIFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }
    private convertControlMechanismFromServer(json: any): GlossaryOfControlMechanisms {
        const entity: GlossaryOfControlMechanisms = Object.assign(new GlossaryOfControlMechanisms(), json);
        return entity;
    }

    //#endregion "Control Mechanisms"

    //#region "Risks"

    public getAllRisks(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/risks`)
            .map((res: Response) => {
                return this.convertResponseWithRisks(res);
            })
    }
    public getAllCurrentRisks(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/risks/current`)
            .map((res: Response) => {
                return this.convertResponseWithRisks(res);
            })
    }
    public getAllUserRisks(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/risks/user`)
            .map((res: Response) => {
                return this.convertResponseWithRisks(res);
            })
    }
    private convertResponseWithRisks(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertRisksFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }
    private convertRisksFromServer(json: any): GlossaryOfRisks {
        const entity: GlossaryOfRisks = Object.assign(new GlossaryOfRisks(), json);
        return entity;
    }

    //#endregion "Risks"
}
