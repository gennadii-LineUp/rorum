import { Injectable } from "@angular/core";
import {SERVER_API_URL} from '../../app.constants';
import { Observable } from "rxjs/Observable";
import { ResponseWrapper, createRequestOption } from "../../shared";
import { Orders } from "../orders";
import { Http, Response, ResponseContentType } from "@angular/http";
import { GlossaryOfProcesses } from "../glossary-of-processes";
import { OrganisationStructure } from "../organisation-structure";

@Injectable()
export class ReportingService {

    private resourceUrl = SERVER_API_URL + 'api/reporting';
    // private resourceUrl = 'reporting';

    constructor(private http: Http) { }

    public getAllProcesses(orderId): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/${orderId}/processes`)
            .map((res: Response) => {
                return this.convertResponseWithProcesses(res);
            })
    }
    private convertResponseWithProcesses(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertProcessesFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to .
     */
    private convertProcessesFromServer(json: any): GlossaryOfProcesses {
        const entity: GlossaryOfProcesses = Object.assign(new GlossaryOfProcesses(), json);
        return entity;
    }

    public loadAll(): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrl)
            .map((res: Response) => {
                return this.convertResponse(res);
            });
    }

    public loadOrder(orderId): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/${orderId}`)
            .map((res: Response) => {
                return this.convertOrder(res);
            });
    }
    private convertOrder(res: Response): ResponseWrapper {
        const result = this.convertItemFromServer(res.json());

        return new ResponseWrapper(res.headers, result, res.status);
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
     * Convert a returned JSON object to Orders.
     */
    private convertItemFromServer(json: any): Orders {
        const entity: Orders = Object.assign(new Orders(), json);
        return entity;
    }

    /**
     * Convert a Orders to a JSON which can be sent to the server.
     */
    private convert(orders: Orders): Orders {
        const copy: Orders = Object.assign({}, orders);
        return copy;
    }
    generatePurposeAccomplishmentRaport(orderId: number, userId: number): Promise<any> {
        return this.http
            .get(`${this.resourceUrl}/${orderId}/excel/purpose-accomplishement/${userId}`, {responseType: ResponseContentType.Blob})
            .toPromise()
            .then((res: Response) => {
                    return new Blob([res.blob()], {});
            })
            .catch((error) => console.log('service error: ' + error));
        }

        generateRisksChangesReport(orderId: number, organisationStructureId: number): Promise<any> {
        return this.http
            .get(`${this.resourceUrl}/${orderId}/${organisationStructureId}/excel/risks-register-raport/`, {responseType: ResponseContentType.Blob})
            .toPromise()
            .then((res: Response) => {
                    return new Blob([res.blob()], {});
            })
            .catch((error) => console.log('service error: ' + error));
        }

    generateRiskRegisterRaport(orderId: number): Promise<any> {
        return this.http
            .get(`${this.resourceUrl}/${orderId}/excel/risks-register-raport/`, {responseType: ResponseContentType.Blob})
            .toPromise()
            .then((res: Response) => {
                    return new Blob([res.blob()], {});
            })
            .catch((error) => console.log('service error: ' + error));
        }

    generateRiskReport(orderId: number): Promise<any> {
        return this.http
            .get(`${this.resourceUrl}/${orderId}/excel/risks-report/`, {responseType: ResponseContentType.Blob})
            .toPromise()
            .then((res: Response) => {
                    return new Blob([res.blob()], {});
            })
            .catch((error) => console.log('service error: ' + error));
        }

    generateRiskRegisterAdmin(orderId: number): Promise<any> {
        return this.http
            .get(`${this.resourceUrl}/${orderId}/excel/risks-register-admin/`, {responseType: ResponseContentType.Blob})
            .toPromise()
            .then((res: Response) => {
                    return new Blob([res.blob()], {});
            })
            .catch((error) => console.log('service error: ' + error));
        }

        // Get All Organisation Structures

    public getAllParentedOrSupervisoredOrganisationStructures(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/organisationStructures`)
            .map((res: Response) => {
                return this.convertResponseOrganisationStructures(res);
            })
    }
    private convertResponseOrganisationStructures(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertProcessesFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }
    private convertOrganisationStructuresFromServer(json: any): OrganisationStructure {
        const entity: OrganisationStructure = Object.assign(new OrganisationStructure(), json);
        return entity;
    }
    generateRiskReportWord(orderId: number): Promise<any> {
        return this.http
            .get(`${this.resourceUrl}/${orderId}/word/risks-report/`, {responseType: ResponseContentType.Blob})
            .toPromise()
            .then((res: Response) => {
                    return new Blob([res.blob()], {});
            })
            .catch((error) => console.log('service error: ' + error));
        }

    generatePurposeAccomplishmentRaportAdmin(orderId: number, organisationStructureId: number): Promise<any> {
        return this.http
            .get(`${this.resourceUrl}/${orderId}/${organisationStructureId}/excel/purpose-accomplishement/`, {responseType: ResponseContentType.Blob})
            .toPromise()
            .then((res: Response) => {
                    return new Blob([res.blob()], {});
            })
            .catch((error) => console.log('service error: ' + error));
        }

}
