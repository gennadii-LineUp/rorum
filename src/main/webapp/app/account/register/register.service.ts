import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

@Injectable()
export class Register {

    constructor(private http: Http) {}

    save(account: any, organisationStructureId: number): Observable<any> {
        const obj = {
            'email': account.email,
            'firstName': account.firstName,
            'lastName': account.lastName,
            'login': account.login,
            'organisationStructureId': organisationStructureId,
            'password': account.password,
        };
        console.log(obj);
        return this.http.post(SERVER_API_URL + 'api/register', obj);
    }

}
