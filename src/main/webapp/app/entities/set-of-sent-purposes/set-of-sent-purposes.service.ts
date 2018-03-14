import {Injectable} from '@angular/core';
import {Headers, Http, Response, ResponseContentType} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {Subject} from 'rxjs/Subject';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {SetOfSentPurposes} from './set-of-sent-purposes.model';
import {createRequestOption, ResponseWrapper} from '../../shared';
import {Orders} from '../orders/models/orders.model';

@Injectable()
export class SetOfSentPurposesService {
    set_purposes_id: number;
    order_id: number;
    parent_type: string;
    statusOfSending: string;
    ids = [];

    private _status = new Subject<boolean>();
    status$ = this._status.asObservable();

    private resourceUrl = SERVER_API_URL + 'api/set-of-sent-purposes';
    private resourceUrl_orders = SERVER_API_URL + 'api/orders';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    setSetPurposesId(set_purposes_id: number) {
        this.set_purposes_id = set_purposes_id;
        // console.log('SERVICE--> ' + this.set_purposes_id);
    }
    getSetPurposesId(): number {
        return this.set_purposes_id;
    }
    setOrderId(order_id: number) {
        this.order_id = order_id;
        // console.log('SERVICE--> ' + this.order_id);
    }
    getOrderId(): number {
        return this.order_id;
    }
    setIds(ids: Array<number>) {
        this.ids = ids;
        // console.log('SERVICE--> ' + this.ids);
    }
    getIds(): Array<number> {
        return this.ids;
    }
    setParentType(parent_type: string) {
        this.parent_type = parent_type;
        // console.log('SERVICE--> ' + this.parent_type);
    }
    getParentType(): string {
        return this.parent_type;
    }

    setStatusOfSending(statusOfSending: string) {
        this.statusOfSending = statusOfSending;
        // console.log('SERVICE--> ' + this.parent_type);
    }
    getStatusOfSending(): string {
        return this.statusOfSending;
    }

    setModalRejectCheckingStatus(status: boolean) {
        this._status.next(status);
    }

    create(setOfSentPurposes: SetOfSentPurposes): Observable<SetOfSentPurposes> {
        const copy = this.convert(setOfSentPurposes);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(setOfSentPurposes: SetOfSentPurposes): Observable<SetOfSentPurposes> {
        const copy = this.convert(setOfSentPurposes);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SetOfSentPurposes> {
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
    query_new(OrderId: number, parent_type: string, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        const url = `${this.resourceUrl_orders}/${OrderId}` + `/set_of_sent_purposes/local_admin/` + parent_type;
        // console.log(url + ' <--CORRECT');
        return this.http.get(url, options)
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
     * Convert a returned JSON object to SetOfSentPurposes.
     */
    private convertItemFromServer(json: any): SetOfSentPurposes {
        const entity: SetOfSentPurposes = Object.assign(new SetOfSentPurposes(), json);
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(json.creationDate);
        return entity;
    }

    /**
     * Convert a SetOfSentPurposes to a JSON which can be sent to the server.
     */
    private convert(setOfSentPurposes: SetOfSentPurposes): SetOfSentPurposes {
        const copy: SetOfSentPurposes = Object.assign({}, setOfSentPurposes);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(setOfSentPurposes.creationDate);
        return copy;
    }

    getSetPurposes(id_order: number, id_set_purpose: number): Observable<Orders> {
        const url = `${this.resourceUrl_orders}/${id_order}/set_of_sent_purposes/${id_set_purpose}`;
        // http://localhost:8080/api/set_of_sent_purposes/1001
        //
        // http://localhost:8080/api/orders/1/set_of_sent_purposes/1001
        //

        console.log(url);
        return this.http.get(url).map((res: Response) => {
            const jsonResponse = res.json();
            // console.log(jsonResponse);
            return jsonResponse;
        });
    }

    approveSetOfPurposesByAdmin(id_order: number, id_set_purpose: number, ids: Array<number>): Observable<Response> {
        const url = `${this.resourceUrl_orders}/${id_order}/set_of_sent_purposes`;
        // console.log(url + '      work');

        const set_purposes = {
            'id': id_set_purpose,
            'ids': ids,
            'statusOfSending': 'CONFIRMED_PURPOSES'
        };
        // console.log(JSON.stringify(set_purposes));
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.put(url, JSON.stringify(set_purposes), {'headers': headers}).map((res: Response) => {
            // const jsonResponse = res.json();
            // console.log(jsonResponse);
            return res;
        });
    }

    approvePlanByAdmin(id_order: number, id_set_purpose: number, ids: Array<number>): Observable<Response> {
        const url = `${this.resourceUrl_orders}/${id_order}/set_of_sent_purposes`;
        console.log(url + '      accept PLAN');

        const set_purposes = {
            'id': id_set_purpose,
            'statusOfSending': 'CONFIRMED_PLAN'
        };
        console.log(JSON.stringify(set_purposes));
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.put(url, JSON.stringify(set_purposes), {'headers': headers}).map((res: Response) => {
            // const jsonResponse = res.json();
            // console.log(jsonResponse);
            return res;
        });
    }

    rejectSetOfPurposesByAdmin(id_order: number, id_set_purpose: number, notation: string, ids: Array<number>): Observable<Response> {
        const url = `${this.resourceUrl_orders}/${id_order}/set_of_sent_purposes`;
        // console.log(url);

        const set_purposes = {
            'id': id_set_purpose,
            'ids': ids,
            'notation': notation,
            'statusOfSending': 'REJECTED_PURPOSES'
        };
        // console.log(JSON.stringify(set_purposes));
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.put(url, JSON.stringify(set_purposes), {'headers': headers}).map((res: Response) => {
            // const jsonResponse = res.json();
            // console.log(jsonResponse);
            // return jsonResponse;
            return res;
        });
    }

    rejectPlanByAdmin(id_order: number, id_set_purpose: number, notation: string): Observable<Response> {
        const url = `${this.resourceUrl_orders}/${id_order}/set_of_sent_purposes`;
        // console.log(url);

        const set_purposes = {
            'id': id_set_purpose,
            'notation': notation,
            'statusOfSending': 'REJECTED_PLAN'
        };
        // console.log(JSON.stringify(set_purposes));
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.put(url, JSON.stringify(set_purposes), {'headers': headers}).map((res: Response) => {
            // const jsonResponse = res.json();
            // console.log(jsonResponse);
            // return jsonResponse;
            return res;
        });
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

}
