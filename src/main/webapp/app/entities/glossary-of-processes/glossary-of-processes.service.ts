import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {GlossaryOfProcesses} from './glossary-of-processes.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class GlossaryOfProcessesService {

    private resourceUrl = SERVER_API_URL + 'api/glossary-of-processes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(glossaryOfProcesses: GlossaryOfProcesses): Observable<GlossaryOfProcesses> {
        const copy = this.convert(glossaryOfProcesses);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(glossaryOfProcesses: GlossaryOfProcesses): Observable<GlossaryOfProcesses> {
        const copy = this.convert(glossaryOfProcesses);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<GlossaryOfProcesses> {
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
     * Convert a returned JSON object to GlossaryOfProcesses.
     */
    private convertItemFromServer(json: any): GlossaryOfProcesses {
        const entity: GlossaryOfProcesses = Object.assign(new GlossaryOfProcesses(), json);
        entity.importantTo = this.dateUtils
            .convertLocalDateFromServer(json.importantTo);
        return entity;
    }

    /**
     * Convert a GlossaryOfProcesses to a JSON which can be sent to the server.
     */
    private convert(glossaryOfProcesses: GlossaryOfProcesses): GlossaryOfProcesses {
        const copy: GlossaryOfProcesses = Object.assign({}, glossaryOfProcesses);
        copy.importantTo = this.dateUtils
            .convertLocalDateToServer(glossaryOfProcesses.importantTo);
        return copy;
    }
}
