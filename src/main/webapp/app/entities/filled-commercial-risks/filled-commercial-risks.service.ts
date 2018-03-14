import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {FilledCommercialRisks} from './filled-commercial-risks.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class FilledCommercialRisksService {

    private resourceUrl = SERVER_API_URL + 'api/filled-commercial-risks';

    constructor(private http: Http) { }

    create(filledCommercialRisks: FilledCommercialRisks): Observable<FilledCommercialRisks> {
        const copy = this.convert(filledCommercialRisks);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(filledCommercialRisks: FilledCommercialRisks): Observable<FilledCommercialRisks> {
        const copy = this.convert(filledCommercialRisks);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<FilledCommercialRisks> {
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
     * Convert a returned JSON object to FilledCommercialRisks.
     */
    private convertItemFromServer(json: any): FilledCommercialRisks {
        const entity: FilledCommercialRisks = Object.assign(new FilledCommercialRisks(), json);
        return entity;
    }

    /**
     * Convert a FilledCommercialRisks to a JSON which can be sent to the server.
     */
    private convert(filledCommercialRisks: FilledCommercialRisks): FilledCommercialRisks {
        const copy: FilledCommercialRisks = Object.assign({}, filledCommercialRisks);
        return copy;
    }
}
