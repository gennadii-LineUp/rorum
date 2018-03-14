import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {HighRisk} from './high-risk.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class HighRiskService {

    private resourceUrl = SERVER_API_URL + 'api/high-risks';

    constructor(private http: Http) { }

    create(highRisk: HighRisk): Observable<HighRisk> {
        const copy = this.convert(highRisk);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(highRisk: HighRisk): Observable<HighRisk> {
        const copy = this.convert(highRisk);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<HighRisk> {
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
     * Convert a returned JSON object to HighRisk.
     */
    private convertItemFromServer(json: any): HighRisk {
        const entity: HighRisk = Object.assign(new HighRisk(), json);
        return entity;
    }

    /**
     * Convert a HighRisk to a JSON which can be sent to the server.
     */
    private convert(highRisk: HighRisk): HighRisk {
        const copy: HighRisk = Object.assign({}, highRisk);
        return copy;
    }
}
