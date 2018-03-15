import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Incident } from './incident.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import {Orders} from "../orders";
import {SetOfSentPurposes} from "../set-of-sent-purposes";
import {GlossaryOfRisks} from "../glossary-of-risks";
import {FilledRisks} from "../filled-risks/filled-risks.model";

@Injectable()
export class IncidentService {

    private resourceUrl = SERVER_API_URL + 'api/incident';
    private resourceUserUrl = SERVER_API_URL + 'api/incident-user';

    constructor(private http: Http) { }

    create(incident: Incident): Observable<Incident> {
        const copy = this.convert(incident);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    createForIncidentForm(incident: Incident): Observable<Response> {
        const copy = this.convert(incident);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            // const jsonResponse = res.json();
            return res;
        });
    }

    update(incident: Incident): Observable<Incident> {
        const copy = this.convert(incident);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Incident> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Incident.
     */
    private convertItemFromServer(json: any): Incident {
        const entity: Incident = Object.assign(new Incident(), json);
        console.log(entity);
        return entity;
    }

    public setSupervisor(incident: Incident): Observable<Incident> {
        const copy = this.convert(incident);
        return this.http.put(this.resourceUrl + '/add-supervisor', copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    /**
     * Convert a Incident to a JSON which can be sent to the server.
     */
    private convert(incident: Incident): Incident {
        const copy: Incident = Object.assign({}, incident);
        return copy;
    }

    /**
     *  LoadAllOrders
     * @returns {Observable<ResponseWrapper>}
     */

    public loadAllOrders(): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUserUrl)
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

    /**
     * Convert a returned JSON object to Orders.
     */
    private convertOrdersItemFromServer(json: any): Orders {
        const entity: Orders = Object.assign(new Orders(), json);
        return entity;
    }

    /**
     *
     * @returns {Observable<ResponseWrapper>}
     */
    public loadSetOfSentPurposes(orderId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUserUrl}/${orderId}`)
            .map((res: Response) => {
                return this.convertResponseWithSetOfSentPurposes(res);
            });
    }

    private convertResponseWithSetOfSentPurposes(res: Response): ResponseWrapper {
        const entity: SetOfSentPurposes = Object.assign(new SetOfSentPurposes(), res.json());
        return new ResponseWrapper(res.headers, entity, res.status);
    }

    public loadRisksForSpecificPurpose(orderId: number, purposeId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUserUrl}/${orderId}/${purposeId}`)
            .map((res: Response) => {
                return this.convertResponseWithRisks(res);
            });
    }

    public loadFilledRiskForIncident(riskId: number, purposeId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUserUrl}/filled/${riskId}/${purposeId}`)
            .map((res: Response) => {
                return res;
            });
    }

    private convertResponseWithRisks(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertRiskItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Orders.
     */
    private convertRiskItemFromServer(json: any): GlossaryOfRisks {
        const entity: GlossaryOfRisks = Object.assign(new GlossaryOfRisks(), json);
        return entity;
    }

    public getAllParentedOrSupervisoredCellsIncidents(orderId: number) {
        return this.http.get(`${this.resourceUrl}/${orderId}/admin`)
            .map((res: Response) => {
                return this.convertResponse(res);
            });
    }

    public getAllParentedOrSupervisoredCellsIncidentsUser(orderId: number) {
        return this.http.get(`${this.resourceUserUrl}/${orderId}/get-all`)
            .map((res: Response) => {
                return this.convertResponse(res);
            });
    }


}
