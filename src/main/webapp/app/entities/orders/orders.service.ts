import {Injectable} from '@angular/core';
import {Headers, Http, Response, ResponseContentType} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {Orders} from './models/orders.model';
import {createRequestOption, ResponseWrapper} from '../../shared';
import {SendDataClass} from './models/send-data.model';

@Injectable()
export class OrdersService {

    private resourceUrl = SERVER_API_URL + 'api/orders';
    private resourceAPI = SERVER_API_URL + 'api';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(orders: Orders): Observable<Orders> {
        const copy = this.convert(orders);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(orders: Orders): Observable<Orders> {
        const copy = this.convert(orders);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Orders> {
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

    getListOfPurposes(OrderId: number): Observable<Orders> {
        const url = `${this.resourceUrl}/${OrderId}` + `/purposes`;
        // console.log(url + '');
        return this.http.get(url).map((res: Response) => {
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

    getListOfSentPurposes(OrderId: number): Observable<Orders> {
        const url = `${this.resourceUrl}/${OrderId}` + `/set_of_sent_purposes/local_admin`;
        console.log(url);
        return this.http.get(url).map((res: Response) => {
            // console.log(res);
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

    getListOfCheckedPurposesForUser(OrderId: number): Observable<Orders> {
        const url = `${this.resourceUrl}/${OrderId}` + `/set_of_sent_purposes/user`;
        console.log(url);
        return this.http.get(url).map((res: Response) => {
            // console.log(res);
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

    // "/percentages-of-calculated-values
    getPercentagesOfCalculatedValues(): Observable<Orders> {
        const url = this.resourceAPI + `/percentages-of-calculated-values`;
        console.log(url + ' --new');
        return this.http.get(url).map((res: Response) => {
            // console.log(res);
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

    // @GetMapping("user/local-admin")
    getLocalAdmin(): Observable<Orders> {
        const url = this.resourceAPI + `/user/local-admin`;
        console.log(url);
        return this.http.get(url).map((res: Response) => {
            // console.log(res);
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

    // @GetMapping("/decision-for-risks")
    getDecisionForRisks(): Observable<Orders> {
        const url = this.resourceAPI + `/decision-for-risks`;
        console.log(url);
        return this.http.get(url).map((res: Response) => {
            // console.log(res);
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

    // @GetMapping("/possibilities-to-improve-risks")
    getPossibilitiesToImproveRisks(): Observable<Orders> {
        const url = this.resourceAPI + `/possibilities-to-improve-risks`;
        console.log(url);
        return this.http.get(url).map((res: Response) => {
            // console.log(res);
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }
    // FindLocalAdminOrganisationStructure
    getOrganisationStructure(id: number): Observable<Orders> {
        const url = this.resourceAPI + `/organisation-structures/${id}`;
        console.log(url);
        return this.http.get(url).map((res: Response) => {
            // console.log(res);
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

    // @GetMapping("/orders/{ordersId}/set_of_sent_purposes/{sspId}")
    getUncheckedPlanAccordion(OrderId: number, sspId: number): Observable<Orders> {
        const url = `${this.resourceUrl}/${OrderId}/set_of_sent_purposes/${sspId}`;
        console.log(url + ' --new');
        return this.http.get(url).map((res: Response) => {
            // console.log(res);
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

    // @PutMapping("/orders/{ordersId}/set_of_sent_purposes")
    sendPlansFromUser(OrderId: number, data: SendDataClass): Observable<any> {
        const url = `${this.resourceUrl}/${OrderId}/set_of_sent_purposes`;
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        console.log(url);
        return this.http.put(url, JSON.stringify(data), {'headers': headers}).map((res: Response) => {
            // return this.http.post(url, JSON.stringify(purposes)).map((res: Response) => {
            // console.log(res.toString());
            // console.log(res);
            // return res.json();
            return res;
        });
    }

    getListOfUsersPurposesForAdmin(OrderId: number, setPurposeId: number): Observable<Orders> {
        const url = `${this.resourceUrl}/${OrderId}/set_of_sent_purposes/${setPurposeId}/purposes`;
        // console.log(url + ' <--REPLACED');
        return this.http.get(url).map((res: Response) => {
            // console.log(res);
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    createSetOfSentPurposes(OrderId: number, data: Array<Number>): Observable<any> {
        const url = `${this.resourceUrl}/${OrderId}` + `/set_of_sent_purposes`;
        const purposes = {'ids': data};
        console.log(JSON.stringify(purposes));
        const headers = new Headers();

        console.log(url);
        headers.append('Content-Type', 'application/json');
        return this.http.post(url, JSON.stringify(purposes), {'headers': headers}).map((res: Response) => {
            // return this.http.post(url, JSON.stringify(purposes)).map((res: Response) => {
            // console.log(res.toString());
            // console.log(res);
            // return res.json();
            return res;
        });
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
        entity.startDate = this.dateUtils
            .convertLocalDateFromServer(json.startDate);
        entity.firstReportingDate = this.dateUtils
            .convertLocalDateFromServer(json.firstReportingDate);
        entity.secondReportingDate = this.dateUtils
            .convertLocalDateFromServer(json.secondReportingDate);
        entity.thirdReportingDate = this.dateUtils
            .convertLocalDateFromServer(json.thirdReportingDate);
        entity.finalDate = this.dateUtils
            .convertLocalDateFromServer(json.finalDate);
        return entity;
    }

    /**
     * Convert a Orders to a JSON which can be sent to the server.
     */
    private convert(orders: Orders): Orders {
        const copy: Orders = Object.assign({}, orders);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(orders.startDate);
        copy.firstReportingDate = this.dateUtils
            .convertLocalDateToServer(orders.firstReportingDate);
        copy.secondReportingDate = this.dateUtils
            .convertLocalDateToServer(orders.secondReportingDate);
        copy.thirdReportingDate = this.dateUtils
            .convertLocalDateToServer(orders.thirdReportingDate);
        copy.finalDate = this.dateUtils
            .convertLocalDateToServer(orders.finalDate);
        return copy;
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
}
