import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {DecisionForRisk} from './decision-for-risk.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class DecisionForRiskService {

    private resourceUrl = SERVER_API_URL + 'api/decision-for-risks';

    constructor(private http: Http) { }

    create(decisionForRisk: DecisionForRisk): Observable<DecisionForRisk> {
        const copy = this.convert(decisionForRisk);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(decisionForRisk: DecisionForRisk): Observable<DecisionForRisk> {
        const copy = this.convert(decisionForRisk);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<DecisionForRisk> {
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
     * Convert a returned JSON object to DecisionForRisk.
     */
    private convertItemFromServer(json: any): DecisionForRisk {
        const entity: DecisionForRisk = Object.assign(new DecisionForRisk(), json);
        return entity;
    }

    /**
     * Convert a DecisionForRisk to a JSON which can be sent to the server.
     */
    private convert(decisionForRisk: DecisionForRisk): DecisionForRisk {
        const copy: DecisionForRisk = Object.assign({}, decisionForRisk);
        return copy;
    }
}
