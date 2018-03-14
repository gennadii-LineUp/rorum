import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {GlossaryOfPurposes} from './glossary-of-purposes.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class GlossaryOfPurposesService {

    private resourceUrl = SERVER_API_URL + 'api/glossary-of-purposes';

    constructor(private http: Http) { }

    create(glossaryOfRisksService: GlossaryOfPurposes): Observable<GlossaryOfPurposes> {
        const copy = this.convert(glossaryOfRisksService);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(glossaryOfRisksService: GlossaryOfPurposes): Observable<GlossaryOfPurposes> {
        const copy = this.convert(glossaryOfRisksService);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<GlossaryOfPurposes> {
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

    queryAll(): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrl + "/all")
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
     * Convert a returned JSON object to GlossaryOfPurposes.
     */
    private convertItemFromServer(json: any): GlossaryOfPurposes {
        const entity: GlossaryOfPurposes = Object.assign(new GlossaryOfPurposes(), json);
        return entity;
    }

    /**
     * Convert a GlossaryOfPurposes to a JSON which can be sent to the server.
     */
    private convert(glossaryOfRisksService: GlossaryOfPurposes): GlossaryOfPurposes {
        const copy: GlossaryOfPurposes = Object.assign({}, glossaryOfRisksService);
        return copy;
    }

    // "/glossary-of-purposes/{purposeId}/glossary-of-measure-units"
    getListOfMeasures(purpose_id: number): Observable<any> {
        const url = `${this.resourceUrl}/${purpose_id}` + `/glossary-of-measure-units`;
        // console.log(url + ' --new');
        return this.http.get(url).map((res: Response) => {
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

    // "/glossary-of-purposes/{purposeId}/glossary-of-risks"
    getListOfRisks(purpose_id: number): Observable<any> {
        const url = `${this.resourceUrl}/${purpose_id}` + `/glossary-of-risks`;
        // console.log(url + ' --new');
        return this.http.get(url).map((res: Response) => {
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

    // @GetMapping("/power-of-influence")
    getListOfPowerOfInfluence(): Observable<any> {
        const url = `api/power-of-influence`;
        // console.log(url + ' --new');
        return this.http.get(url).map((res: Response) => {
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

    getListOfProbabilities(): Observable<any> {
        const url = `api/probabilities`;
        // console.log(url + ' --new');
        return this.http.get(url).map((res: Response) => {
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

}
