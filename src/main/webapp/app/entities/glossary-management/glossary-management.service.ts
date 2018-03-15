import { Injectable } from "@angular/core";
import {SERVER_API_URL} from '../../app.constants';
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {ResponseWrapper} from "../../shared";
import {GlossaryOfProcesses} from "../glossary-of-processes";
import {GlossaryOfPurposes} from "../glossary-of-purposes";
import {GlossaryOfMeasureUnits} from "../glossary-of-measure-units";

@Injectable()
export class GlossaryManagementService {

    private resourceUrl = SERVER_API_URL + 'api/glossary-management';
    constructor(private http: Http) { }

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


    public getAllPurposes(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/purposes`)
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

    public getAllMeasures(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/measures`)
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
}
