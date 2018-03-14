import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {GlossaryOfMeasureUnits} from './glossary-of-measure-units.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class GlossaryOfMeasureUnitsService {

    private resourceUrl = SERVER_API_URL + 'api/glossary-of-measure-units';

    constructor(private http: Http) { }

    create(glossaryOfMeasureUnitsDTO: GlossaryOfMeasureUnits): Observable<GlossaryOfMeasureUnits> {
        const copy = this.convert(glossaryOfMeasureUnitsDTO);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(glossaryOfMeasureUnitsDTO: GlossaryOfMeasureUnits): Observable<GlossaryOfMeasureUnits> {
        const copy = this.convert(glossaryOfMeasureUnitsDTO);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<GlossaryOfMeasureUnits> {
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
     * Convert a returned JSON object to GlossaryOfMeasureUnits.
     */
    private convertItemFromServer(json: any): GlossaryOfMeasureUnits {
        const entity: GlossaryOfMeasureUnits = Object.assign(new GlossaryOfMeasureUnits(), json);
        return entity;
    }

    /**
     * Convert a GlossaryOfMeasureUnits to a JSON which can be sent to the server.
     */
    private convert(glossaryOfMeasureUnitsDTO: GlossaryOfMeasureUnits): GlossaryOfMeasureUnits {
        const copy: GlossaryOfMeasureUnits = Object.assign({}, glossaryOfMeasureUnitsDTO);
        return copy;
    }
}
