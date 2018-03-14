import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {GlossaryOfCommercialRisks} from './glossary-of-commercial-risks.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class GlossaryOfCommercialRisksService {

    private resourceUrl = SERVER_API_URL + 'api/glossary-of-commercial-risks';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(glossaryOfCommercialRisksDTO: GlossaryOfCommercialRisks): Observable<GlossaryOfCommercialRisks> {
        const copy = this.convert(glossaryOfCommercialRisksDTO);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(glossaryOfCommercialRisksDTO: GlossaryOfCommercialRisks): Observable<GlossaryOfCommercialRisks> {
        const copy = this.convert(glossaryOfCommercialRisksDTO);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<GlossaryOfCommercialRisks> {
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
     * Convert a returned JSON object to GlossaryOfCommercialRisks.
     */
    private convertItemFromServer(json: any): GlossaryOfCommercialRisks {
        const entity: GlossaryOfCommercialRisks = Object.assign(new GlossaryOfCommercialRisks(), json);
        entity.completionDate = this.dateUtils
            .convertLocalDateFromServer(json.completionDate);
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(json.creationDate);
        entity.importantTo = this.dateUtils
            .convertLocalDateFromServer(json.importantTo);
        return entity;
    }

    /**
     * Convert a GlossaryOfCommercialRisks to a JSON which can be sent to the server.
     */
    private convert(glossaryOfCommercialRisksDTO: GlossaryOfCommercialRisks): GlossaryOfCommercialRisks {
        const copy: GlossaryOfCommercialRisks = Object.assign({}, glossaryOfCommercialRisksDTO);
        copy.completionDate = this.dateUtils
            .convertLocalDateToServer(glossaryOfCommercialRisksDTO.completionDate);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(glossaryOfCommercialRisksDTO.creationDate);
        copy.importantTo = this.dateUtils
            .convertLocalDateToServer(glossaryOfCommercialRisksDTO.importantTo);
        return copy;
    }
}
