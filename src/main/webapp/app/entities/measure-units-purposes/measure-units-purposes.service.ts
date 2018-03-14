import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {MeasureUnitsPurposes} from './measure-units-purposes.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class MeasureUnitsPurposesService {

    private resourceUrl = SERVER_API_URL + 'api/measure-units-purposes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(measureUnitsPurposes: MeasureUnitsPurposes): Observable<MeasureUnitsPurposes> {
        const copy = this.convert(measureUnitsPurposes);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(measureUnitsPurposes: MeasureUnitsPurposes): Observable<MeasureUnitsPurposes> {
        const copy = this.convert(measureUnitsPurposes);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MeasureUnitsPurposes> {
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
     * Convert a returned JSON object to MeasureUnitsPurposes.
     */
    private convertItemFromServer(json: any): MeasureUnitsPurposes {
        const entity: MeasureUnitsPurposes = Object.assign(new MeasureUnitsPurposes(), json);
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(json.creationDate);
        return entity;
    }

    /**
     * Convert a MeasureUnitsPurposes to a JSON which can be sent to the server.
     */
    private convert(measureUnitsPurposes: MeasureUnitsPurposes): MeasureUnitsPurposes {
        const copy: MeasureUnitsPurposes = Object.assign({}, measureUnitsPurposes);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(measureUnitsPurposes.creationDate);
        return copy;
    }
}
