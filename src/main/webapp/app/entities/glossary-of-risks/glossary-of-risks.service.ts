import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {GlossaryOfRisks} from './glossary-of-risks.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class GlossaryOfRisksService {

    private resourceUrl = SERVER_API_URL + 'api/glossary-of-risks';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(glossaryOfRisksDTO: GlossaryOfRisks): Observable<GlossaryOfRisks> {
        const copy = this.convert(glossaryOfRisksDTO);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(glossaryOfRisksDTO: GlossaryOfRisks): Observable<GlossaryOfRisks> {
        const copy = this.convert(glossaryOfRisksDTO);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<GlossaryOfRisks> {
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
     * Convert a returned JSON object to GlossaryOfRisks.
     */
    private convertItemFromServer(json: any): GlossaryOfRisks {
        const entity: GlossaryOfRisks = Object.assign(new GlossaryOfRisks(), json);
        entity.completionDate = this.dateUtils
            .convertLocalDateFromServer(json.completionDate);
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(json.creationDate);
        entity.importantTo = this.dateUtils
            .convertLocalDateFromServer(json.importantTo);
        return entity;
    }

    /**
     * Convert a GlossaryOfRisks to a JSON which can be sent to the server.
     */
    private convert(glossaryOfRisksDTO: GlossaryOfRisks): GlossaryOfRisks {
        const copy: GlossaryOfRisks = Object.assign({}, glossaryOfRisksDTO);
        copy.completionDate = this.dateUtils
            .convertLocalDateToServer(glossaryOfRisksDTO.completionDate);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(glossaryOfRisksDTO.creationDate);
        copy.importantTo = this.dateUtils
            .convertLocalDateToServer(glossaryOfRisksDTO.importantTo);
        return copy;
    }
}
