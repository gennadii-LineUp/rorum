import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

@Injectable()
export class GlossaryOfControlMechanismsService {

    private resourceUrl = SERVER_API_URL + 'api/glossary-of-control-mechanisms';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

}
