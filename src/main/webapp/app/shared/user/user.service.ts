import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {SERVER_API_URL} from '../../app.constants';
import {User} from './user.model';
import {ResponseWrapper} from '../model/response-wrapper.model';
import {createRequestOption} from '../model/request-util';
import {GlossaryOfProcesses} from "../../entities/glossary-of-processes";

@Injectable()
export class UserService {
    private resourceUrl = SERVER_API_URL + 'api/users';

    constructor(private http: Http) { }

    create(user: User): Observable<ResponseWrapper> {
        return this.http.post(this.resourceUrl, user)
            .map((res: Response) => this.convertResponse(res));
    }

    update(user: User): Observable<ResponseWrapper> {
        return this.http.put(this.resourceUrl, user)
            .map((res: Response) => this.convertResponse(res));
    }

    find(login: string): Observable<User> {
        return this.http.get(`${this.resourceUrl}/${login}`).map((res: Response) => res.json());
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(login: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${login}`);
    }

    authorities(): Observable<string[]> {
        return this.http.get(SERVER_API_URL + 'api/users/authorities').map((res: Response) => {
            const json = res.json();
            return <string[]> json;
        });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }


    public getAllUsers(): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrl)
            .map((res: Response) => {
                return this.convertResponseWithUsers(res);
            })
    }
    private convertResponseWithUsers(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertUsersFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to .
     */
    private convertUsersFromServer(json: any): User {
        const entity: User = Object.assign(new User(), json);
        return entity;
    }
}
