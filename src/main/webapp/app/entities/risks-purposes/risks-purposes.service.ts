import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {RisksPurposes} from './risks-purposes.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class RisksPurposesService {

    private resourceUrl = SERVER_API_URL + 'api/risks-purposes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(risksPurposes: RisksPurposes): Observable<RisksPurposes> {
        const copy = this.convert(risksPurposes);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(risksPurposes: RisksPurposes): Observable<RisksPurposes> {
        const copy = this.convert(risksPurposes);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<RisksPurposes> {
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
     * Convert a returned JSON object to RisksPurposes.
     */
    private convertItemFromServer(json: any): RisksPurposes {
        const entity: RisksPurposes = Object.assign(new RisksPurposes(), json);
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(json.creationDate);
        return entity;
    }

    /**
     * Convert a RisksPurposes to a JSON which can be sent to the server.
     */
    private convert(risksPurposes: RisksPurposes): RisksPurposes {
        const copy: RisksPurposes = Object.assign({}, risksPurposes);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(risksPurposes.creationDate);
        return copy;
    }
}
