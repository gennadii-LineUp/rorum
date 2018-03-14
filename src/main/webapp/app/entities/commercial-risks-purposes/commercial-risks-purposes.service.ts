import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {CommercialRisksPurposes} from './commercial-risks-purposes.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class CommercialRisksPurposesService {

    private resourceUrl = SERVER_API_URL + 'api/commercial-risks-purposes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(commercialRisksPurposes: CommercialRisksPurposes): Observable<CommercialRisksPurposes> {
        const copy = this.convert(commercialRisksPurposes);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(commercialRisksPurposes: CommercialRisksPurposes): Observable<CommercialRisksPurposes> {
        const copy = this.convert(commercialRisksPurposes);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CommercialRisksPurposes> {
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
     * Convert a returned JSON object to CommercialRisksPurposes.
     */
    private convertItemFromServer(json: any): CommercialRisksPurposes {
        const entity: CommercialRisksPurposes = Object.assign(new CommercialRisksPurposes(), json);
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(json.creationDate);
        return entity;
    }

    /**
     * Convert a CommercialRisksPurposes to a JSON which can be sent to the server.
     */
    private convert(commercialRisksPurposes: CommercialRisksPurposes): CommercialRisksPurposes {
        const copy: CommercialRisksPurposes = Object.assign({}, commercialRisksPurposes);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(commercialRisksPurposes.creationDate);
        return copy;
    }
}
