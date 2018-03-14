import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {OrganisationStructure} from './organisation-structure.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class OrganisationStructureService {

    private resourceUrl = SERVER_API_URL + 'api/organisation-structures';

    constructor(private http: Http) { }

    create(organisationStructure: OrganisationStructure): Observable<OrganisationStructure> {
        const copy = this.convert(organisationStructure);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(organisationStructure: OrganisationStructure): Observable<OrganisationStructure> {
        const copy = this.convert(organisationStructure);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<OrganisationStructure> {
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
     * Convert a returned JSON object to OrganisationStructure.
     */
    private convertItemFromServer(json: any): OrganisationStructure {
        const entity: OrganisationStructure = Object.assign(new OrganisationStructure(), json);
        return entity;
    }

    /**
     * Convert a OrganisationStructure to a JSON which can be sent to the server.
     */
    private convert(organisationStructure: OrganisationStructure): OrganisationStructure {
        const copy: OrganisationStructure = Object.assign({}, organisationStructure);
        return copy;
    }

    // @GetMapping("/organisation-structures")
    getAllOrganizations(): Observable<ResponseWrapper> {
        const url = `${this.resourceUrl}?size=10000`;
        console.log(url);
        return this.http.get(url)
            .map((res: Response) => this.convertResponse(res));
    }

    // @GetMapping("/organisation-structures/glossary-of-commercial-risks")
    getListOfCommercialRisks(): Observable<any> {
        const url = `${this.resourceUrl}/glossary-of-commercial-risks`;
        // console.log(url + ' --very new');
        return this.http.get(url).map((res: Response) => {
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

}
