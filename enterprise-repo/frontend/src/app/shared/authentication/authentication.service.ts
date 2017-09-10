import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { User } from './user';

@Injectable()
export class AuthenticationService {

    private user: User;

    private headers = new Headers({ 'Content-Type': 'application/json' });
    private loginApiUrl = 'api/v1/auth/login/';

    constructor(private http: Http) {
        this.loadUserFromCookies().then(user => {
            this.user = user;
        });
    }

    private loadUserFromCookies(): Promise<User> {
        // TODO: load user info from cookies
        return Promise.resolve(null);
    }

    getAuthenticatedUser(): User {
        return this.user;
    }

    isAuthenticated(): boolean {
        return !!this.user;
    }

    setAuthenticatedUser(user: User) {
        this.user = user;
        // TODO: store user info from cookies
    }

    unauthenticate() {
        this.user = null;
        // TODO: clear user info from cookies
    }

    login(username: string, password: string): Promise<User> {
        const data = JSON.stringify({
            'username': username,
            'password': password
        });
        return this.http.post(this.loginApiUrl, data, { headers: this.headers })
            .toPromise()
            .then(resp => {
                this.setAuthenticatedUser(resp.json() as User);
                return this.getAuthenticatedUser();
            })
            .catch(error => {
                return Promise.reject(error);
            });
    }
}