import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { User } from './user';

@Injectable()
export class AuthenticationService {

    private headers = new Headers({ 'Content-Type': 'application/json' });
    private loginApiUrl = 'api/v1/auth/login/';

    constructor(private http: Http) {
    }

    login(username: string, password: string): Promise<User> {
        const data = JSON.stringify({
            'username': username,
            'password': password
        });
        return this.http.post(this.loginApiUrl, data, { headers: this.headers })
            .toPromise()
            .then(resp => resp.json().data as User)
            .catch(error => {
                return Promise.reject(error);
            });
    }
}