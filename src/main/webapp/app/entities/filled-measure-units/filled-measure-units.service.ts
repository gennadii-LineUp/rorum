import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {FilledMeasureUnits} from './filled-measure-units.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class FilledMeasureUnitsService {

    private resourceUrl = SERVER_API_URL + 'api/filled-measure-units';

    constructor(private http: Http) { }

    create(filledMeasureUnits: FilledMeasureUnits): Observable<FilledMeasureUnits> {
        const copy = this.convert(filledMeasureUnits);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(filledMeasureUnits: FilledMeasureUnits): Observable<FilledMeasureUnits> {
        const copy = this.convert(filledMeasureUnits);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<FilledMeasureUnits> {
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
     * Convert a returned JSON object to FilledMeasureUnits.
     */
    private convertItemFromServer(json: any): FilledMeasureUnits {
        const entity: FilledMeasureUnits = Object.assign(new FilledMeasureUnits(), json);
        return entity;
    }

    /**
     * Convert a FilledMeasureUnits to a JSON which can be sent to the server.
     */
    private convert(filledMeasureUnits: FilledMeasureUnits): FilledMeasureUnits {
        const copy: FilledMeasureUnits = Object.assign({}, filledMeasureUnits);
        return copy;
    }
}
