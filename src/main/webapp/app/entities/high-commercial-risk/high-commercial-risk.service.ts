import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { HighCommercialRisk } from './high-commercial-risk.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class HighCommercialRiskService {

    private resourceUrl = SERVER_API_URL + 'api/high-commercial-risks';

    constructor(private http: Http) { }

    create(highCommercialRisk: HighCommercialRisk): Observable<HighCommercialRisk> {
        const copy = this.convert(highCommercialRisk);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(highCommercialRisk: HighCommercialRisk): Observable<HighCommercialRisk> {
        const copy = this.convert(highCommercialRisk);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<HighCommercialRisk> {
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
     * Convert a returned JSON object to HighCommercialRisk.
     */
    private convertItemFromServer(json: any): HighCommercialRisk {
        const entity: HighCommercialRisk = Object.assign(new HighCommercialRisk(), json);
        return entity;
    }

    /**
     * Convert a HighCommercialRisk to a JSON which can be sent to the server.
     */
    private convert(highCommercialRisk: HighCommercialRisk): HighCommercialRisk {
        const copy: HighCommercialRisk = Object.assign({}, highCommercialRisk);
        return copy;
    }
}
